package com.arbelkilani.bingetv.data.repositories.tv

import android.util.Log
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.base.ResponseHandler
import com.arbelkilani.bingetv.data.model.genre.Genre
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

        return try {
            val response = apiService.getAiringToday()

            if (response.isSuccessful && response.body() != null) {
                //genreDao.saveGenres(response.body()!!.genres)
                responseHandler.handleSuccess(response.code(), response.body()!!)
            } else {
                responseHandler.handleFailure(response.code(), response.message())
            }

        } catch (e: Exception) {
            Log.i(TAG, "exception {${e.printStackTrace()}}")
            responseHandler.handleFailure(-1, e.localizedMessage!!)
            //TODO define -1 error
        }
    }

}