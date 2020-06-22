package com.arbelkilani.bingetv.data.source.remote

import com.arbelkilani.bingetv.data.model.tv.maze.NextEpisodeData
import com.arbelkilani.bingetv.data.model.tv.maze.TvDetailsMaze
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiTvMazeService {

    @GET("lookup/shows")
    suspend fun getShow(
        @Query("imdb") imdbId: String, @Query("embed") embed: String
    ): TvDetailsMaze

    @GET("episodes/{episode_id}")
    suspend fun getNextEpisode(@Path("episode_id") nextEpisodeId: String): NextEpisodeData
}