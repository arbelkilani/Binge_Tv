package com.arbelkilani.bingetv.data.repositories.tv

import android.util.Log
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.credit.CreditsResponse
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.data.model.tv.TvDetails
import com.arbelkilani.bingetv.data.source.remote.ApiTmdbService
import com.arbelkilani.bingetv.data.source.remote.ApiTvMazeService
import com.arbelkilani.bingetv.domain.repositories.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

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

    override suspend fun getTvDetails(id: Int): Resource<TvDetails> {
        Log.i(TAG, "getTvDetails()")
        //TODO check handling error
        //TODO adding append_to_response

        try {
            val test = apiTvMazeService.getShow("tt2661044", "nextepisode")
            Log.i(TAG, "test = $test")
        } catch (e: Exception) {
            e.printStackTrace()
        }


        return try {
            val response = apiTmdbService.getTvDetails(id, "videos")
            Resource.success(response)
        } catch (e: Exception) {
            Log.i(TAG, "exception : ${e.localizedMessage}")
            Resource.exception(e, null)
        }
    }

    override suspend fun getCredits(id: Int): Resource<CreditsResponse> {
        Log.i(TAG, "getCredits()")
        //TODO check handling error
        //TODO adding append_to_response
        return try {
            val response = apiTmdbService.getCredits(id)
            Resource.success(response)
        } catch (e: Exception) {
            Resource.exception(e, null)
        }
    }
}