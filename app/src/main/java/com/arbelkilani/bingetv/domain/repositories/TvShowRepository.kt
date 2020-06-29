package com.arbelkilani.bingetv.domain.repositories

import androidx.paging.PagingData
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.credit.CreditsResponse
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.data.model.tv.TvDetails
import com.arbelkilani.bingetv.data.model.tv.maze.details.NextEpisodeData
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {
    suspend fun trending(): Flow<ApiResponse<Tv>>
    suspend fun discover(): Flow<PagingData<Tv>>
    suspend fun airingToday(): Flow<PagingData<Tv>>
    suspend fun popular(): Flow<PagingData<Tv>>
    suspend fun onTheAir(): Flow<PagingData<Tv>>

    suspend fun getTvDetails(id: Int): Resource<TvDetails>
    suspend fun getCredits(id: Int): Resource<CreditsResponse>
    suspend fun getNextEpisodeData(id: Int): Resource<NextEpisodeData>
    suspend fun searchTvShow(query: String): Resource<ApiResponse<Tv>>
}