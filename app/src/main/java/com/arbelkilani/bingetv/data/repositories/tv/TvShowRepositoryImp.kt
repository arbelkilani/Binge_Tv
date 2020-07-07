package com.arbelkilani.bingetv.data.repositories.tv

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arbelkilani.bingetv.data.entities.base.ApiResponse
import com.arbelkilani.bingetv.data.entities.base.Resource
import com.arbelkilani.bingetv.data.entities.credit.CreditsResponse
import com.arbelkilani.bingetv.data.entities.season.SeasonData
import com.arbelkilani.bingetv.data.entities.tv.TvShowData
import com.arbelkilani.bingetv.data.entities.tv.maze.TvDetailsMaze
import com.arbelkilani.bingetv.data.entities.tv.maze.channel.WebChannel
import com.arbelkilani.bingetv.data.entities.tv.maze.details.NextEpisodeData
import com.arbelkilani.bingetv.data.mappers.season.SeasonMapper
import com.arbelkilani.bingetv.data.mappers.tv.TvShowMapper
import com.arbelkilani.bingetv.data.source.local.season.SeasonDao
import com.arbelkilani.bingetv.data.source.local.tv.TvDao
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTmdbService
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTvMazeService
import com.arbelkilani.bingetv.data.source.remote.pagingsource.*
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.domain.repositories.TvShowRepository
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
    private val seasonDao: SeasonDao
) : TvShowRepository {

    private val tvShowMapper = TvShowMapper()
    private val seasonMapper = SeasonMapper()

    companion object {
        private const val PAGE_SIZE = 20
        private const val TAG = "TvShowRepository"
    }

    override suspend fun trending(): Flow<ApiResponse<TvShowData>> {
        Log.i(TAG, "trending()")
        return flow { emit(apiTmdbService.trending("tv", "day")) }
    }

    override suspend fun discover(): Flow<PagingData<TvShowEntity>> {
        Log.i(TAG, "discover()")
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { DiscoverPagingSource(apiTmdbService) }
        ).flow
    }

    override suspend fun airingToday(): Flow<PagingData<TvShowData>> {
        Log.i(TAG, "airingToday()")
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { AiringTodayPagingSource(apiTmdbService) }
        ).flow
    }

    override suspend fun popular(): Flow<PagingData<TvShowData>> {
        Log.i(TAG, "popular()")
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { PopularPagingSource(apiTmdbService) }
        ).flow
    }

    override suspend fun onTheAir(): Flow<PagingData<TvShowEntity>> {
        Log.i(TAG, "onTheAir()")
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { OnTheAirPagingSource(apiTmdbService) }
        ).flow
    }

    override suspend fun search(query: String): Flow<PagingData<TvShowData>> {
        Log.i(TAG, "search($query)")
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { SearchPagingSource(query, apiTmdbService) }
        ).flow
    }

    override suspend fun getTvDetails(id: Int): Resource<TvShowEntity> =
        try {
            Log.i(TAG, "getTvDetails() for item $id")
            val tvShowData = apiTmdbService.getTvDetails(id, "videos,images")

            tvDao.getTvShow(id)?.let { localTvShow ->
                tvShowData.watched = localTvShow.watched
                tvShowData.watchlist = localTvShow.watchlist
                tvShowData.seasons.map { it.watched = localTvShow.watched }
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
            tvShowEntity.watched = watched
            tvShowEntity.seasons.map {
                it.watched = tvShowEntity.watched
                if (tvShowEntity.watched) {
                    it.watchedEpisodeCount = it.episodeCount
                    it.progress = (it.watchedEpisodeCount / it.episodeCount) * 100
                } else {
                    it.watchedEpisodeCount = 0
                    it.progress = 0
                }
            }

            tvDao.saveTv(tvShowMapper.mapFromEntity(tvShowEntity))

            for (seasonEntity in tvShowEntity.seasons) {
                val seasonData = seasonMapper.mapFromEntity(seasonEntity)
                seasonData.tv_season = tvShowEntity.id
                seasonData.watched = tvShowEntity.watched
                seasonDao.saveSeason(seasonData)
            }

            return tvShowEntity
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
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


    override suspend fun getSeasonDetails(tvId: Int, seasonNumber: Int): Resource<SeasonData> =
        try {
            Log.i(TAG, "getSeasonDetails() for tv id $tvId and season number $seasonNumber")
            val response = apiTmdbService.getSeasonDetails(tvId, seasonNumber)
            Resource.success(response)
        } catch (e: Exception) {
            Resource.exception(e, null)
        }

    private suspend fun saveGenres(tvShow: TvShowData) {
        val genres = tvShow.genres
        for (item in genres) {
            item.tv_genre = tvShow.id
            tvDao.saveGenre(item)
        }
    }

    private suspend fun saveNetworks(tvShow: TvShowData) {
        val networks = tvShow.networks
        for (item in networks) {
            item.tv_network = tvShow.id
            tvDao.saveNetworks(item)
        }
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
            Log.i(TAG, "getCredits() for item $id")
            val response = apiTmdbService.getCredits(id)
            Resource.success(response)
        } catch (e: Exception) {
            Log.e(TAG, "exception = $e")
            Resource.exception(e, null)
        }

    override suspend fun getNextEpisodeData(id: Int): Resource<NextEpisodeData> {
        Log.i(TAG, "getNextEpisodeData() for item $id")
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