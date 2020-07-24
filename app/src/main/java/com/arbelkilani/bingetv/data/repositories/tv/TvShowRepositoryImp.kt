package com.arbelkilani.bingetv.data.repositories.tv

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arbelkilani.bingetv.data.entities.base.Resource
import com.arbelkilani.bingetv.data.entities.credit.CreditsResponse
import com.arbelkilani.bingetv.data.entities.external.ExternalIds
import com.arbelkilani.bingetv.data.entities.tv.TvShowData
import com.arbelkilani.bingetv.data.entities.tv.maze.details.NextEpisodeData
import com.arbelkilani.bingetv.data.mappers.season.SeasonMapper
import com.arbelkilani.bingetv.data.mappers.tv.TvShowMapper
import com.arbelkilani.bingetv.data.source.local.genre.GenreDao
import com.arbelkilani.bingetv.data.source.local.season.SeasonDao
import com.arbelkilani.bingetv.data.source.local.tv.NextEpisodeDao
import com.arbelkilani.bingetv.data.source.local.tv.TvDao
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTmdbService
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTvMazeService
import com.arbelkilani.bingetv.data.source.remote.pagingsource.*
import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.domain.repositories.TvShowRepository
import com.arbelkilani.bingetv.utils.filterEpisodeAirDate
import com.arbelkilani.bingetv.utils.time
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


@FlowPreview
@ExperimentalCoroutinesApi
class TvShowRepositoryImp(
    private val apiTmdbService: ApiTmdbService,
    private val apiTvMazeService: ApiTvMazeService,
    private val tvDao: TvDao,
    private val seasonDao: SeasonDao,
    private val genreDao: GenreDao,
    private val nextEpisodeDao: NextEpisodeDao
) : TvShowRepository {

    private val tvShowMapper = TvShowMapper()
    private val seasonMapper = SeasonMapper()

    companion object {
        private const val TAG = "TvShowRepository"
        private const val PAGE_SIZE = 20
        private const val EMBED_NEXT_EPISODE = "nextepisode"
    }

    override suspend fun trending(): Flow<List<TvShowEntity>> {
        val data = apiTmdbService.trending("tv", "day")
        val entities = data.results.map { tvShowMapper.mapToEntity(it) }
        return flow { emit(entities) }
    }

    override suspend fun discover(): Flow<PagingData<TvShowEntity>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { DiscoverPagingSource(apiTmdbService) }
        ).flow
    }

    override suspend fun airingToday(): Flow<PagingData<TvShowData>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { AiringTodayPagingSource(apiTmdbService) }
        ).flow
    }

    override suspend fun popular(): Flow<PagingData<TvShowData>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { PopularPagingSource(apiTmdbService) }
        ).flow
    }

    override suspend fun onTheAir(): Flow<PagingData<TvShowEntity>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { OnTheAirPagingSource(apiTmdbService) }
        ).flow
    }

    override suspend fun search(query: String): Flow<PagingData<TvShowEntity>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { SearchPagingSource(query, apiTmdbService) }
        ).flow
    }

    override suspend fun recommendations(id: Int): Flow<PagingData<TvShowEntity>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { RecommendationsPagingSource(id, apiTmdbService) }
        ).flow
    }

    override suspend fun getTvShowDetails(id: Int): Resource<TvShowEntity> =
        try {
            val tvShowRemote = apiTmdbService.getTvDetails(id, "videos")
            val copyTvShowRemote = tvShowRemote.copy()

            copyTvShowRemote.apply {

                futureEpisodesCount = futureEpisodesCount(copyTvShowRemote)

                /**
                 * update season state
                 * add future episode count to last season to handle it's UI changes (radio button)
                 */
                seasons.let {
                    it.last().futureEpisodeCount = futureEpisodesCount
                    it.map { remote ->
                        seasonDao.getSeason(remote.id)?.let { local ->
                            remote.futureEpisodeCount = local.futureEpisodeCount
                            remote.watched = local.watched
                            remote.watchedCount = local.watchedCount
                        }
                    }
                }

                /**
                 * update tv show state
                 * depending on state saved in database -> update the remote object.
                 */
                tvDao.getTvShow(id)?.let {
                    watched = it.watched
                    watchlist = it.watchlist
                    watchedCount = it.watchedCount
                }

                nextEpisode = nextEpisode()
            }

            try {
                Resource.success(tvShowMapper.mapToEntity(copyTvShowRemote))
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.exception(e, null)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.exception(e, null)
        }

    /**
     * check if tv show still in production (copyTvShowRemote.inProduction)
     * and if there is future episode to air -> then get their count
     * the idea is to get the last season (lastSeason) obviously, and filter its episodes by air_date in order to
     * get which episode air_date is set to future regarding to current date.
     */
    private suspend fun TvShowData.futureEpisodesCount(
        tvShowData: TvShowData
    ): Int {
        return if (inProduction) {
            val last = tvShowData.seasons.last()
            val details =
                apiTmdbService.getSeasonDetails(tvId = id, seasonNumber = last.seasonNumber)

            details.episodes.filter { episodeData -> filterEpisodeAirDate(episodeData.airDate) }.size
        } else 0
    }

    /**
     * to get next episode details we get service from tvMaze
     * two calls should be done {lookup/shows} and {episodes/{episode_id}}
     * from the first api we get the alternative id from tvMaze
     * from the second api we get the detailed data and we format the air_stamp to time
     * in order to manipulate it in UI.
     */
    private suspend fun TvShowData.nextEpisode(): NextEpisodeData? {
        return try {
            val externalIds = externalIds(id)
            val show = apiTvMazeService.getShow(externalIds?.imdbId, EMBED_NEXT_EPISODE)
            val showId = show?.links?.nextEpisode?.href?.substringAfterLast("/")

            val episode = apiTvMazeService.getNextEpisode(showId)
            episode.time = episode.time()
            episode.tv_next_episode = id
            //nextEpisodeDao.saveNextEpisode(episode) //TODO
            episode

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }

    private suspend fun saveTvShow(tvShowEntity: TvShowEntity) {
        val tvShowData = tvShowMapper.mapFromEntity(tvShowEntity)
        tvDao.saveTv(tvShowData)
    }

    private suspend fun saveSeason(seasonEntity: SeasonEntity, tvShowEntity: TvShowEntity) {
        val seasonData = seasonMapper.mapFromEntity(seasonEntity)
        seasonData.tv_season = tvShowEntity.id
        seasonDao.saveSeason(seasonData)
    }

    private suspend fun saveGenre(tvShowEntity: TvShowEntity) {

        val local = tvDao.getTvShow(tvShowEntity.id)
        val diff = tvShowEntity.episodeCount - tvShowEntity.futureEpisodesCount

        if (local == null) {
            tvShowEntity.genres.map {
                genreDao.incrementCount(it.id, 1)
            }
        } else {
            if (tvShowEntity.watchedCount == 0) {
                tvShowEntity.genres.map {
                    genreDao.incrementCount(it.id, -1)
                }
            }

            if (tvShowEntity.watchedCount == diff && tvShowEntity.watched) {
                tvShowEntity.genres.map {
                    genreDao.incrementCount(it.id, 1)
                }
            }
        }
    }

    override suspend fun saveWatched(watched: Boolean, tvShowEntity: TvShowEntity): TvShowEntity? {

        try {

            tvShowEntity.watched = watched
            tvShowEntity.watchlist = if (watched) false else tvShowEntity.watchlist
            tvShowEntity.watchedCount =
                if (watched) (tvShowEntity.episodeCount - tvShowEntity.futureEpisodesCount) else 0

            tvShowEntity.seasons.let { seasonList ->
                val last = seasonList.last()
                last.futureEpisodeCount = tvShowEntity.futureEpisodesCount
                seasonList.map { season ->
                    season.watchedCount =
                        if (tvShowEntity.watched) (season.episodeCount - season.futureEpisodeCount) else 0
                    season.watched = season.episodeCount == season.watchedCount
                    saveSeason(season, tvShowEntity)
                }
            }

            saveGenre(tvShowEntity)
            saveTvShow(tvShowEntity)

            return tvShowEntity
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    override suspend fun watchlist(): List<TvShowEntity> {
        val tvShows = tvDao.watchlist(true)
        return tvShows?.map { tvShowData -> tvShowMapper.mapToEntity(tvShowData) }!!
    }

    override suspend fun watched(): List<TvShowEntity> {
        val tvShows = tvDao.watched()
        return tvShows?.map { tvShowData -> tvShowMapper.mapToEntity(tvShowData) }!!
    }

    override suspend fun saveWatchlist(
        watchlist: Boolean,
        tvShowEntity: TvShowEntity
    ): TvShowEntity? =
        try {
            tvShowEntity.watchlist = watchlist
            tvDao.saveTv(tvShowMapper.mapFromEntity(tvShowEntity))
            tvShowEntity
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    override suspend fun getCredits(id: Int): Resource<CreditsResponse> =
        try {
            val response = apiTmdbService.getCredits(id)
            Resource.success(response)
        } catch (e: Exception) {
            Log.e(TAG, "exception = $e")
            Resource.exception(e, null)
        }

    override suspend fun getNextEpisodeData(id: Int): Resource<NextEpisodeData> {
        return Resource.exception(Exception(), null)
    }

    private suspend fun externalIds(id: Int): ExternalIds? {
        return apiTmdbService.getExternalIds(id)
    }

}