package com.arbelkilani.bingetv.data.repositories.tv

import android.util.Log
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.data.model.tv.TvDetails
import com.arbelkilani.bingetv.data.source.remote.ApiService
import com.arbelkilani.bingetv.domain.repositories.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class TvShowRepositoryImp(
    private val apiService: ApiService
) : TvShowRepository {

    private val TAG = TvShowRepository::class.java.simpleName

    override suspend fun getAiringToday(): Flow<ApiResponse<Tv>> {
        Log.i(TAG, "getAiringToday()")
        return flow { emit(apiService.getAiringToday()) }
    }

    override suspend fun getTrendingTv(): Flow<ApiResponse<Tv>> {
        Log.i(TAG, "getTrendingTv()")
        return flow { emit(apiService.getTrendingTv()) }
    }

    override suspend fun getTvDetails(id: Int): Resource<TvDetails> {
        Log.i(TAG, "getTvDetails()")
        try {
            val response = apiService.getTvDetails(id)
            return Resource.success(response)
        } catch (e: Exception) {
            return Resource.exception(e, null)
        }

    }
}