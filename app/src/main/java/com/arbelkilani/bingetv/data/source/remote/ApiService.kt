package com.arbelkilani.bingetv.data.source.remote

import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.credit.CreditsResponse
import com.arbelkilani.bingetv.data.model.genre.GenreResponse
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.data.model.tv.TvDetails
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("genre/tv/list")
    suspend fun getGenres(): Response<GenreResponse>

    @GET("discover/tv?sort_by=popularity.desc")
    suspend fun getAiringToday(): ApiResponse<Tv>

    @GET("trending/tv/week")
    suspend fun getTrendingTv(): ApiResponse<Tv>

    @GET("tv/{tv_id}")
    suspend fun getTvDetails(
        @Path("tv_id") id: Int,
        @Query("append_to_response") appendToResponse: String
    ): TvDetails

    @GET("tv/{tv_id}/credits")
    suspend fun getCredits(@Path("tv_id") id: Int): CreditsResponse
}