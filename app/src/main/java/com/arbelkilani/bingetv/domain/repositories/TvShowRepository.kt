package com.arbelkilani.bingetv.domain.repositories

import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.data.model.tv.TvDetails
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {
    suspend fun getAiringToday(): Flow<ApiResponse<Tv>>
    suspend fun getTrendingTv(): Flow<ApiResponse<Tv>>
    suspend fun getTvDetails(id: Int): Resource<TvDetails>
}