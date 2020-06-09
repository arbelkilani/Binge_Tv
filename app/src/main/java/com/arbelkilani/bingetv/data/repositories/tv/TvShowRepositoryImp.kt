package com.arbelkilani.bingetv.data.repositories.tv

import android.util.Log
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.data.source.remote.ApiService
import com.arbelkilani.bingetv.domain.repositories.TvShowRepository
import retrofit2.Response

class TvShowRepositoryImp(
    private val apiService: ApiService
) : TvShowRepository {

    private val TAG = TvShowRepository::class.java.simpleName

    override suspend fun getAiringToday(): Resource<ApiResponse<Tv>> {
        Log.i(TAG, "getAiringToday()")
        return try {
            fetchRemoteData(apiService.getAiringToday())
        } catch (e: Exception) {
            Resource.exception(e)
        }
    }

    override suspend fun getTrendingTv(): Resource<ApiResponse<Tv>> {
        Log.i(TAG, "getTrendingTv()")
        return try {
            fetchRemoteData(apiService.getTrendingTv())
        } catch (e: Exception) {
            Resource.exception(e)
        }
    }

    private fun fetchRemoteData(response: Response<ApiResponse<Tv>>): Resource<ApiResponse<Tv>> {
        return if (response.isSuccessful) {
            Resource.success(response.body())
        } else {
            Resource.error(response.errorBody()!!)
        }
    }


}