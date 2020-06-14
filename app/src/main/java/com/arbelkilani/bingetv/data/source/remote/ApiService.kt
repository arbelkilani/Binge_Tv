package com.arbelkilani.bingetv.data.source.remote

import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.genre.GenreResponse
import com.arbelkilani.bingetv.data.model.tv.Tv
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("genre/tv/list")
    suspend fun getGenres(): Response<GenreResponse>

    @GET("tv/airing_today")
    suspend fun getAiringToday(): ApiResponse<Tv>

    @GET("trending/tv/week")
    suspend fun getTrendingTv(): ApiResponse<Tv>
}