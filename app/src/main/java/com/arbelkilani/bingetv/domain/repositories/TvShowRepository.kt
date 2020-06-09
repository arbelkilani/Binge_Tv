package com.arbelkilani.bingetv.domain.repositories

import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.tv.Tv

interface TvShowRepository {
    suspend fun getAiringToday(): Resource<ApiResponse<Tv>>

    suspend fun getTrendingTv(): Resource<ApiResponse<Tv>>
}