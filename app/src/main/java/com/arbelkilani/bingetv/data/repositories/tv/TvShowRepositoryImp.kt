package com.arbelkilani.bingetv.data.repositories.tv

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arbelkilani.bingetv.data.entities.base.Resource
import com.arbelkilani.bingetv.data.entities.credit.CreditsResponse
import com.arbelkilani.bingetv.data.entities.tv.TvShowData
import com.arbelkilani.bingetv.data.entities.tv.maze.TvDetailsMaze
import com.arbelkilani.bingetv.data.entities.tv.maze.channel.WebChannel
import com.arbelkilani.bingetv.data.entities.tv.maze.details.NextEpisodeData
import com.arbelkilani.bingetv.data.mappers.season.SeasonMapper
import com.arbelkilani.bingetv.data.mappers.tv.TvShowMapper
import com.arbelkilani.bingetv.data.source.local.genre.GenreDao
import com.arbelkilani.bingetv.data.source.local.season.SeasonDao
import com.arbelkilani.bingetv.data.source.local.tv.TvDao
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTmdbService
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTvMazeService
import com.arbelkilani.bingetv.data.source.remote.pagingsource.*
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.domain.repositories.TvShowRepository
import com.arbelkilani.bingetv.utils.checkAirDate
import com.arbelkilani.bingetv.utils.formatAirDate
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
    private val genreDao: GenreDao
) : TvShowRepository {

    private val tvShowMapper = TvShowMapper()
    private val seasonMapper = SeasonMapper()

    companion object {
        private const val PAGE_SIZE = 20
        private const val TAG = "TvShowRepository"
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

    override suspend fun tvShowEntityResponse(id: Int): Resource<TvShowEntity> =
        try {
            val tvShowData = apiTmdbService.getTvDetails(id, "videos")
            val localSeasons = seasonDao.getSeasons(id)
            var futureEpisodesCount = 0

            // this tv show is still running -> handle future episodes states.
            // get last season episodes and count future episodes in order to get real episodes count aired.
            if (tvShowData.inProduction) {

                val lastSeason = tvShowData.seasons[tvShowData.seasons.size - 1]
                val lastSeasonDetails = apiTmdbService.getSeasonDetails(
                    tvId = tvShowData.id,
                    seasonNumber = lastSeason.seasonNumber
                )

                futureEpisodesCount =
                    lastSeasonDetails.episodes.filter { checkAirDate(it.airDate) }.size

            }

            tvShowData.futureEpisodesCount = futureEpisodesCount

            tvShowData.seasons.let {
                if (futureEpisodesCount > 0)
                    it.last().futureEpisodeCount = futureEpisodesCount
                it.map { remote ->
                    localSeasons?.map { local ->
                        if (remote.id == local.id) {
                            remote.futureEpisodeCount = local.futureEpisodeCount
                            remote.watched = local.watched
                            remote.watchedCount = local.watchedCount
                        }
                    }
                }
            }

            tvDao.getTvShow(id)?.let { localTvShow ->
                tvShowData.watched = localTvShow.watched
                tvShowData.watchlist = localTvShow.watchlist
                tvShowData.watchedCount = localTvShow.watchedCount
            }

            try {
                val nextEpisodeData = getNextEpisodeData(id)
                tvShowData.nextEpisode = nextEpisodeData.data
                Resource.success(tvShowMapper.mapToEntity(tvShowData))
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.exception(e, null)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.exception(e, null)
        }

    override suspend fun saveWatched(watched: Boolean, tvShowEntity: TvShowEntity): TvShowEntity? {

        try {

            val localTvShow = tvDao.getTvShow(tvShowEntity.id)

            if (watched)
                tvShowEntity.watchlist = false

            tvShowEntity.watched = watched
            tvShowEntity.seasons.map { seasonEntity ->

                // check if at most one season get future episodes.
                // if future episodes exists, get related season and update it
                // else update other seasons standard.
                val seasonWatchedCount: Int =
                    if (tvShowEntity.futureEpisodesCount > 0) {
                        if (seasonEntity.seasonNumber == tvShowEntity.seasons.size) {
                            if (tvShowEntity.watched) (seasonEntity.episodeCount - tvShowEntity.futureEpisodesCount) else 0
                        } else {
                            if (tvShowEntity.watched) seasonEntity.episodeCount else 0
                        }
                    } else {
                        if (tvShowEntity.watched) seasonEntity.episodeCount else 0
                    }

                seasonEntity.watchedCount = seasonWatchedCount
                seasonEntity.futureEpisodeCount =
                    if (tvShowEntity.futureEpisodesCount > 0 && seasonEntity.seasonNumber == tvShowEntity.seasons.size) tvShowEntity.futureEpisodesCount else 0

                seasonEntity.watched = seasonEntity.watchedCount == seasonEntity.episodeCount
                val seasonData = seasonMapper.mapFromEntity(seasonEntity)
                seasonData.tv_season = tvShowEntity.id

                seasonDao.saveSeason(seasonData)
            }

            tvShowEntity.watchedCount =
                if (watched) (tvShowEntity.episodeCount - tvShowEntity.futureEpisodesCount) else 0

            if (localTvShow == null) {
                tvShowEntity.genres.map {
                    genreDao.incrementCount(it.id, 1)
                }
            } else {
                if (tvShowEntity.watchedCount == 0) {
                    tvShowEntity.genres.map {
                        genreDao.incrementCount(it.id, -1)
                    }
                }

                if (tvShowEntity.watchedCount == (tvShowEntity.episodeCount - tvShowEntity.futureEpisodesCount) && tvShowEntity.watched) {
                    tvShowEntity.genres.map {
                        genreDao.incrementCount(it.id, 1)
                    }
                }
            }

            tvDao.saveTv(tvShowMapper.mapFromEntity(tvShowEntity))

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

    private suspend fun saveNextEpisode(tvShow: TvShowData) {
        val nextEpisode = tvShow.nextEpisodeToAir
        nextEpisode?.let {
            it.tv_next_episode = tvShow.id
            tvDao.saveNextEpisode(it)
        }
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
        val nextEpisodeHashMap = getNextEpisodeId(id)
        if (nextEpisodeHashMap!!.isEmpty())
            return Resource.exception(Exception("next episode id is empty"), null)

        return try {
            val response = apiTvMazeService.getNextEpisode(nextEpisodeHashMap[0]!!)
            response.timezone = nextEpisodeHashMap[1]!!
            response.formattedAirDate = formatAirDate(response)
            Resource.success(response)
        } catch (e: Exception) {
            Resource.exception(e, null)
        }
    }

    private suspend fun getImdbId(id: Int): String = try {
        val externalIdsResponse = apiTmdbService.getExternalIds(id)
        externalIdsResponse.imdbId!!.toString()
    } catch (e: Exception) {
        ""
    }

    private suspend fun getNextEpisodeId(id: Int): HashMap<Int, String>? =
        try {
            val response = apiTvMazeService.getShow(getImdbId(id), "nextepisode")
            val links = response.links
            val values = hashMapOf<Int, String>()
            if (links?.nextEpisode != null) {
                values[0] = links.nextEpisode.href.substringAfterLast("/")
                if (getChannel(response)?.country != null) {
                    val timezone = getChannel(response)?.country!!.timezone
                    values[1] = timezone
                }
                values
            } else {
                Log.e(TAG, "exception either on links or next episode values")
                hashMapOf()
            }
        } catch (e: Exception) {
            hashMapOf()
        }

    private fun getChannel(response: TvDetailsMaze): WebChannel? {
        return response.network ?: response.webChannel
    }

}