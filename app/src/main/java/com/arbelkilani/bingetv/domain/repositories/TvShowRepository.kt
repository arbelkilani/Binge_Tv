package com.arbelkilani.bingetv.domain.repositories

import androidx.paging.PagingData
import com.arbelkilani.bingetv.data.entities.base.Resource
import com.arbelkilani.bingetv.data.entities.credit.CreditsResponse
import com.arbelkilani.bingetv.data.entities.tv.TvShowData
import com.arbelkilani.bingetv.data.entities.tv.maze.details.NextEpisodeData
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {

    suspend fun trending(): Flow<List<TvShowEntity>>
    suspend fun discover(): Flow<PagingData<TvShowEntity>>
    suspend fun airingToday(): Flow<PagingData<TvShowData>>
    suspend fun popular(): Flow<PagingData<TvShowData>>
    suspend fun onTheAir(): Flow<PagingData<TvShowEntity>>
    suspend fun search(query: String): Flow<PagingData<TvShowEntity>>

    suspend fun getTvShowDetails(id: Int): Resource<TvShowEntity>
    suspend fun getCredits(id: Int): Resource<CreditsResponse>
    suspend fun getNextEpisodeData(id: Int): Resource<NextEpisodeData>

    suspend fun saveWatchlist(watchlist: Boolean, tvShowEntity: TvShowEntity): TvShowEntity?
    suspend fun saveWatched(watched: Boolean, tvShowEntity: TvShowEntity): TvShowEntity?

    suspend fun watchlist(): List<TvShowEntity>
    suspend fun recommendations(id: Int): Flow<PagingData<TvShowEntity>>
    suspend fun watched(): List<TvShowEntity>

}