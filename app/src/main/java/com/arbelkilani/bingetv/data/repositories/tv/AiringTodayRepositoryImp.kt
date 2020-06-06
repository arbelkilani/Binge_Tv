package com.arbelkilani.bingetv.data.repositories.tv

import android.util.Log
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.base.ResponseHandler
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.data.source.remote.ApiService
import com.arbelkilani.bingetv.domain.repositories.AiringTodayRepository

class AiringTodayRepositoryImp(
    private val apiService: ApiService,
    private val responseHandler: ResponseHandler
) : AiringTodayRepository {

    private val TAG = AiringTodayRepository::class.java.simpleName

    override suspend fun getAiringToday(): Resource<ApiResponse<Tv>> {
        return fetchRemote()
    }

    private suspend fun fetchRemote(): Resource<ApiResponse<Tv>> {
        Log.i(TAG, "fetchRemote()")
        return try {
            val response = apiService.getAiringToday()
            return if (response.isSuccessful && response.body() != null) {
                Resource.success(response.body()!!, response.code())
            } else {
                Log.i(TAG, "fetchRemote() error : ${response.code()}")
                Resource.error(null, response.code())
            }

        } catch (e: Exception) {
            Log.i(TAG, "fetchRemote() exception : ${e.localizedMessage}")
            Resource.error(null, -1)
        }
    }


}