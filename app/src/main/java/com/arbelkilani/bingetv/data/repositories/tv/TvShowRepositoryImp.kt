package com.arbelkilani.bingetv.data.repositories.tv

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.credit.CreditsResponse
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.data.model.tv.TvDetails
import com.arbelkilani.bingetv.data.model.tv.maze.TvDetailsMaze
import com.arbelkilani.bingetv.data.model.tv.maze.channel.WebChannel
import com.arbelkilani.bingetv.data.model.tv.maze.details.NextEpisodeData
import com.arbelkilani.bingetv.data.pagingsource.AiringTodayPagingSource
import com.arbelkilani.bingetv.data.pagingsource.DiscoverPagingSource
import com.arbelkilani.bingetv.data.pagingsource.OnTheAirPagingSource
import com.arbelkilani.bingetv.data.pagingsource.PopularPagingSource
import com.arbelkilani.bingetv.data.source.remote.ApiTmdbService
import com.arbelkilani.bingetv.data.source.remote.ApiTvMazeService
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
    private val apiTvMazeService: ApiTvMazeService
) : TvShowRepository {

    private val TAG = TvShowRepositoryImp::class.java.simpleName

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        private const val NETWORK_PAGE_SIZE = 20
    }

    override suspend fun trending(): Flow<ApiResponse<Tv>> {
        Log.i(TAG, "trending()")
        return flow { emit(apiTmdbService.trending("tv", "day")) }
    }

    override suspend fun discover(): Flow<PagingData<Tv>> {
        Log.i(TAG, "discover()")
        return Pager(
            config = PagingConfig(NETWORK_PAGE_SIZE),
            pagingSourceFactory = { DiscoverPagingSource(apiTmdbService) }
        ).flow
    }

    override suspend fun airingToday(): Flow<PagingData<Tv>> {
        Log.i(TAG, "airingToday()")
        return Pager(
            config = PagingConfig(NETWORK_PAGE_SIZE),
            pagingSourceFactory = { AiringTodayPagingSource(apiTmdbService) }
        ).flow
    }

    override suspend fun popular(): Flow<PagingData<Tv>> {
        Log.i(TAG, "popular()")
        return Pager(
            config = PagingConfig(NETWORK_PAGE_SIZE),
            pagingSourceFactory = { PopularPagingSource(apiTmdbService) }
        ).flow
    }

    override suspend fun onTheAir(): Flow<PagingData<Tv>> {
        Log.i(TAG, "onTheAir()")
        return Pager(
            config = PagingConfig(NETWORK_PAGE_SIZE),
            pagingSourceFactory = { OnTheAirPagingSource(apiTmdbService) }
        ).flow
    }

    override suspend fun getTvDetails(id: Int): Resource<TvDetails> = try {
        Log.i(TAG, "getTvDetails() for item $id")
        val response = apiTmdbService.getTvDetails(id, "videos")
        Resource.success(response)
    } catch (e: Exception) {
        Resource.exception(e, null)
    }

    override suspend fun getCredits(id: Int): Resource<CreditsResponse> = try {
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

    override suspend fun searchTvShow(query: String): Resource<ApiResponse<Tv>> = try {
        val response = apiTmdbService.searchTvShow(query, false)
        Resource.success(response)
    } catch (e: Exception) {
        Resource.exception(e, null)
    }

    private suspend fun getImdbId(id: Int): String = try {
        val externalIdsResponse = apiTmdbService.getExternalIds(id)
        externalIdsResponse.imdbId!!.toString()
    } catch (e: Exception) {
        ""
    }

    private suspend fun getNextEpisodeId(id: Int): HashMap<Int, String>? = try {
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