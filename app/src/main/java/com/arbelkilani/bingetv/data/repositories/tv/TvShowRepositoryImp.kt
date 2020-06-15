package com.arbelkilani.bingetv.data.repositories.tv

import TvDetails
import android.util.Log
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.data.source.remote.ApiService
import com.arbelkilani.bingetv.domain.repositories.TvShowRepository
import com.google.gson.JsonElement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

    override suspend fun getTvDetails(id: Int): Flow<JsonElement> {
        Log.i(TAG, "getTvDetails()")
        Log.i(TAG, "response = ${apiService.getTvDetails(id)}")
        return flow { emit(apiService.getTvDetails(id)) }
    }
}