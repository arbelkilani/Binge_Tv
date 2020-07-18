package com.arbelkilani.bingetv.data.source.remote.apiservice

import com.arbelkilani.bingetv.data.entities.base.ApiResponse
import com.arbelkilani.bingetv.data.entities.credit.CreditsResponse
import com.arbelkilani.bingetv.data.entities.external.ExternalIds
import com.arbelkilani.bingetv.data.entities.genre.GenreResponse
import com.arbelkilani.bingetv.data.entities.season.SeasonData
import com.arbelkilani.bingetv.data.entities.tv.TvShowData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiTmdbService {

    @GET("trending/{media_type}/{time_window}")
    suspend fun trending(
        @Path("media_type") mediaType: String,
        @Path("time_window") timeWindow: String
    ): ApiResponse<TvShowData>

    @GET("discover/tv")
    suspend fun discover(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String
    ): ApiResponse<TvShowData>

    @GET("tv/airing_today")
    suspend fun airingToday(
        @Query("page") page: Int
    ): ApiResponse<TvShowData>

    @GET("tv/popular")
    suspend fun popular(
        @Query("page") page: Int
    ): ApiResponse<TvShowData>

    @GET("tv/on_the_air")
    suspend fun onTheAir(
        @Query("page") page: Int
    ): ApiResponse<TvShowData>

    @GET("search/tv")
    suspend fun search(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("include_adult") adult: Boolean
    ): ApiResponse<TvShowData>

    @GET("tv/{tv_id}/similar")
    suspend fun recommendations(
        @Path("tv_id") tvId: Int,
        @Query("page") page: Int
    ): ApiResponse<TvShowData>


    @GET("tv/{tv_id}/season/{season_number}")
    suspend fun getSeasonDetails(
        @Path("tv_id") tvId: Int,
        @Path("season_number") seasonNumber: Int
    ): SeasonData

    @GET("genre/tv/list")
    suspend fun getGenres(): Response<GenreResponse>


    @GET("tv/{tv_id}")
    suspend fun getTvDetails(
        @Path("tv_id") id: Int,
        @Query("append_to_response") appendToResponse: String
    ): TvShowData

    @GET("tv/{tv_id}/credits")
    suspend fun getCredits(@Path("tv_id") id: Int): CreditsResponse

    @GET("tv/{tv_id}/external_ids")
    suspend fun getExternalIds(@Path("tv_id") id: Int): ExternalIds
}