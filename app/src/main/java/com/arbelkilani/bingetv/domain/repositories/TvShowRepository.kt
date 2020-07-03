package com.arbelkilani.bingetv.domain.repositories

import androidx.paging.PagingData
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.credit.CreditsResponse
import com.arbelkilani.bingetv.data.model.season.SeasonDetails
import com.arbelkilani.bingetv.data.model.tv.TvShow
import com.arbelkilani.bingetv.data.model.tv.maze.details.NextEpisodeData
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {

    suspend fun trending(): Flow<ApiResponse<TvShow>>
    suspend fun discover(): Flow<PagingData<TvShow>>
    suspend fun airingToday(): Flow<PagingData<TvShow>>
    suspend fun popular(): Flow<PagingData<TvShow>>
    suspend fun onTheAir(): Flow<PagingData<TvShow>>
    suspend fun search(query: String): Flow<PagingData<TvShow>>


    suspend fun getTvDetails(id: Int): Resource<TvShow>
    suspend fun getCredits(id: Int): Resource<CreditsResponse>
    suspend fun getNextEpisodeData(id: Int): Resource<NextEpisodeData>
    suspend fun getSeasonDetails(tvId: Int, seasonNumber: Int): Resource<SeasonDetails>

    suspend fun saveToWatchlist(tv: TvShow)
    suspend fun setTvShowWatched(tv: TvShow)

}