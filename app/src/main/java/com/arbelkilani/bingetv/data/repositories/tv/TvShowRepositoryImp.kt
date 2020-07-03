package com.arbelkilani.bingetv.data.repositories.tv

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.credit.CreditsResponse
import com.arbelkilani.bingetv.data.model.season.SeasonDetails
import com.arbelkilani.bingetv.data.model.tv.TvShow
import com.arbelkilani.bingetv.data.model.tv.maze.TvDetailsMaze
import com.arbelkilani.bingetv.data.model.tv.maze.channel.WebChannel
import com.arbelkilani.bingetv.data.model.tv.maze.details.NextEpisodeData
import com.arbelkilani.bingetv.data.source.local.tv.TvDao
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTmdbService
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTvMazeService
import com.arbelkilani.bingetv.data.source.remote.pagingsource.*
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
    private val tvDao: TvDao
) : TvShowRepository {

    private val TAG = TvShowRepositoryImp::class.java.simpleName

    companion object {
        private const val PAGE_SIZE = 20
    }

    override suspend fun trending(): Flow<ApiResponse<TvShow>> {
        Log.i(TAG, "trending()")
        return flow { emit(apiTmdbService.trending("tv", "day")) }
    }

    override suspend fun discover(): Flow<PagingData<TvShow>> {
        Log.i(TAG, "discover()")
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { DiscoverPagingSource(apiTmdbService) }
        ).flow
    }

    override suspend fun airingToday(): Flow<PagingData<TvShow>> {
        Log.i(TAG, "airingToday()")
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { AiringTodayPagingSource(apiTmdbService) }
        ).flow
    }

    override suspend fun popular(): Flow<PagingData<TvShow>> {
        Log.i(TAG, "popular()")
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { PopularPagingSource(apiTmdbService) }
        ).flow
    }

    override suspend fun onTheAir(): Flow<PagingData<TvShow>> {
        Log.i(TAG, "onTheAir()")
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { OnTheAirPagingSource(apiTmdbService) }
        ).flow
    }

    override suspend fun search(query: String): Flow<PagingData<TvShow>> {
        Log.i(TAG, "search($query)")
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { SearchPagingSource(query, apiTmdbService) }
        ).flow
    }

    override suspend fun getTvDetails(id: Int): Resource<TvShow> =
        try {
            Log.i(TAG, "getTvDetails() for item $id")
            val remoteItem = apiTmdbService.getTvDetails(id, "videos")
            try {
                val localItem = tvDao.getTvShow(id)

                if (localItem?.id == remoteItem.id) {
                    remoteItem.watchlist = localItem.watchlist
                    remoteItem.watched = localItem.watched
                }

            } catch (e: Exception) {
                Log.i(TAG, "e : exception = ${e.localizedMessage}")
            }

            Resource.success(remoteItem)
        } catch (e: Exception) {
            Log.i(TAG, "exception get details = ${e.localizedMessage}")
            Resource.exception(e, null)
        }

    override suspend fun getSeasonDetails(tvId: Int, seasonNumber: Int): Resource<SeasonDetails> =
        try {
            Log.i(TAG, "getSeasonDetails() for tv id $tvId and season number $seasonNumber")
            val response = apiTmdbService.getSeasonDetails(tvId, seasonNumber)
            Resource.success(response)
        } catch (e: Exception) {
            Resource.exception(e, null)
        }

    override suspend fun saveWatchlist(tvShow: TvShow) {
        try {
            tvDao.saveTv(tvShow)
            val nextEpisode = tvShow.nextEpisodeToAir
            nextEpisode?.let {
                it.tv_details_id = tvShow.id
                tvDao.saveNextEpisode(it)
            }
        } catch (e: Exception) {
            Log.i(TAG, "saveWatchlist e : ${e.localizedMessage}")
        }
    }

    override suspend fun saveWatched(tvShow: TvShow) {
        try {
            tvDao.saveTv(tvShow)
            val nextEpisode = tvShow.nextEpisodeToAir
            nextEpisode?.let {
                it.tv_details_id = tvShow.id
                tvDao.saveNextEpisode(it)
            }
        } catch (e: Exception) {
            Log.i(TAG, "saveWatched e : ${e.localizedMessage}")
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