package com.arbelkilani.bingetv.data.repositories.tv

import android.util.Log
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.credit.CreditsResponse
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.data.model.tv.TvDetails
import com.arbelkilani.bingetv.data.model.tv.maze.NextEpisodeData
import com.arbelkilani.bingetv.data.source.remote.ApiTmdbService
import com.arbelkilani.bingetv.data.source.remote.ApiTvMazeService
import com.arbelkilani.bingetv.domain.repositories.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TvShowRepositoryImp(
    private val apiTmdbService: ApiTmdbService,
    private val apiTvMazeService: ApiTvMazeService
) : TvShowRepository {

    private val TAG = TvShowRepository::class.java.simpleName

    override suspend fun getAiringToday(): Flow<ApiResponse<Tv>> {
        Log.i(TAG, "getAiringToday()")
        return flow { emit(apiTmdbService.getAiringToday()) }
    }

    override suspend fun getTrendingTv(): Flow<ApiResponse<Tv>> {
        Log.i(TAG, "getTrendingTv()")
        return flow { emit(apiTmdbService.getTrendingTv()) }
    }

    override suspend fun getTvDetails(id: Int): Resource<TvDetails> = try {
        Log.i(TAG, "getTrendingTv() for item $id")
        val response = apiTmdbService.getTvDetails(id, "videos")
        Resource.success(response)
    } catch (e: Exception) {
        Log.i(TAG, "exception : ${e.localizedMessage}")
        Resource.exception(e, null)
    }


    override suspend fun getCredits(id: Int): Resource<CreditsResponse> = try {
        Log.i(TAG, "getCredits() for item $id")
        val response = apiTmdbService.getCredits(id)
        Resource.success(response)
    } catch (e: Exception) {
        Resource.exception(e, null)
    }

    override suspend fun getNextEpisodeData(id: Int): Resource<NextEpisodeData> {
        Log.i(TAG, "getNextEpisodeData() for item $id")
        val nextEpisodeId = getNextEpisodeId(id)
        if (nextEpisodeId!!.isEmpty())
            return Resource.exception(Exception("next episode id is empty"), null)

        return try {
            val response = apiTvMazeService.getNextEpisode(nextEpisodeId)
            Resource.success(response)
        } catch (e: Exception) {
            Resource.exception(e, null)
        }
    }

    private suspend fun getImdbId(id: Int): String = try {
        val externalIdsResponse = apiTmdbService.getExternalIds(id)
        externalIdsResponse.imdbId!!.toString()
    } catch (e: Exception) {
        Log.e(TAG, "getImdbId exception $e")
        ""
    }

    private suspend fun getNextEpisodeId(id: Int): String? = try {
        val response = apiTvMazeService.getShow(getImdbId(id), "nextepisode")
        val links = response.links

        if (links?.nextEpisode != null) {
            links.nextEpisode.href.substringAfterLast("/")
        } else {
            Log.e(TAG, "exception either on links or next episode values")
            ""
        }
    } catch (e: Exception) {
        Log.e(TAG, "getNextEpisodeId exception $e")
        ""
    }

}