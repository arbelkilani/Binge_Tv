package com.arbelkilani.bingetv.domain.repositories

import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.tv.Tv
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface TvShowRepository {
    suspend fun getAiringToday(): Flow<ApiResponse<Tv>>
    suspend fun getTrendingTv(): Flow<ApiResponse<Tv>>
}