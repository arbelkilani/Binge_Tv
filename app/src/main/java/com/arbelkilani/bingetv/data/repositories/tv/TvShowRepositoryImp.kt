package com.arbelkilani.bingetv.data.repositories.tv

import android.util.Log
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.base.RepoResult
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.credit.CreditsResponse
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.data.model.tv.TvDetails
import com.arbelkilani.bingetv.data.model.tv.maze.TvDetailsMaze
import com.arbelkilani.bingetv.data.model.tv.maze.channel.WebChannel
import com.arbelkilani.bingetv.data.model.tv.maze.details.NextEpisodeData
import com.arbelkilani.bingetv.data.source.remote.ApiTmdbService
import com.arbelkilani.bingetv.data.source.remote.ApiTvMazeService
import com.arbelkilani.bingetv.domain.repositories.TvShowRepository
import com.arbelkilani.bingetv.utils.formatAirDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


@FlowPreview
@ExperimentalCoroutinesApi
class TvShowRepositoryImp(
    private val apiTmdbService: ApiTmdbService,
    private val apiTvMazeService: ApiTvMazeService
) : TvShowRepository {

    private val TAG = TvShowRepositoryImp::class.java.simpleName

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    private val airingTodayResults = ConflatedBroadcastChannel<RepoResult>()

    private var lastRequestedPage = STARTING_PAGE_INDEX
    private var isRequestInProgress = false

    override suspend fun getAiringToday(): Flow<ApiResponse<Tv>> {
        Log.i(TAG, "getAiringToday()")
        lastRequestedPage = 1
        request()
        return flow { emit(apiTmdbService.getAiringToday(lastRequestedPage)) }
    }


    override suspend fun test(): Flow<RepoResult> {
        lastRequestedPage = 1
        request()
        return airingTodayResults.asFlow()
    }


    override suspend fun requestMore() {
        if (isRequestInProgress) return
        val successful = request()
        if (successful) {
            Log.i(TAG, "request more updating index")
            lastRequestedPage++
        }
    }

    private suspend fun request(): Boolean {
        Log.i(TAG, "request")

        isRequestInProgress = true
        var successful = false

        try {
            Log.i(TAG, "lastRequestedPage $lastRequestedPage")
            val response = apiTmdbService.getAiringToday(lastRequestedPage)
            val results = response.results
            Log.i(TAG, "results $results")
            airingTodayResults.offer(RepoResult.Success(response.results))
            successful = true
        } catch (exception: IOException) {
            Log.e(TAG, "exception = ${exception.localizedMessage}")
            airingTodayResults.offer(RepoResult.Error(exception))
        } catch (exception: HttpException) {
            Log.e(TAG, "exception = ${exception.localizedMessage}")
            airingTodayResults.offer(RepoResult.Error(exception))
        }
        isRequestInProgress = false
        return successful
    }


    override suspend fun getTrendingTv(): Flow<ApiResponse<Tv>> {
        Log.i(TAG, "getTrendingTv()")
        return flow { emit(apiTmdbService.getTrendingTv()) }
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