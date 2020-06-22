package com.arbelkilani.bingetv.data.repositories.tv

import android.util.Log
import com.arbelkilani.bingetv.data.model.base.ApiResponse
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TvShowRepositoryImp(
    private val apiTmdbService: ApiTmdbService,
    private val apiTvMazeService: ApiTvMazeService
) : TvShowRepository {

    private val TAG = TvShowRepositoryImp::class.java.simpleName

    override suspend fun getAiringToday(): Flow<ApiResponse<Tv>> {
        Log.i(TAG, "getAiringToday()")
        return flow { emit(apiTmdbService.getAiringToday()) }
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
        Log.i(TAG, "credits = $response")
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