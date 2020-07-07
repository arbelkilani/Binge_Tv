package com.arbelkilani.bingetv.domain.repositories

import androidx.paging.PagingData
import com.arbelkilani.bingetv.data.entities.base.ApiResponse
import com.arbelkilani.bingetv.data.entities.base.Resource
import com.arbelkilani.bingetv.data.entities.credit.CreditsResponse
import com.arbelkilani.bingetv.data.entities.season.SeasonData
import com.arbelkilani.bingetv.data.entities.tv.TvShowData
import com.arbelkilani.bingetv.data.entities.tv.maze.details.NextEpisodeData
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {

    suspend fun trending(): Flow<ApiResponse<TvShowData>>
    suspend fun discover(): Flow<PagingData<TvShowEntity>>
    suspend fun airingToday(): Flow<PagingData<TvShowData>>
    suspend fun popular(): Flow<PagingData<TvShowData>>
    suspend fun onTheAir(): Flow<PagingData<TvShowEntity>>
    suspend fun search(query: String): Flow<PagingData<TvShowData>>

    suspend fun tvShowEntityResponse(id: Int): Resource<TvShowEntity>
    suspend fun getCredits(id: Int): Resource<CreditsResponse>
    suspend fun getNextEpisodeData(id: Int): Resource<NextEpisodeData>
    suspend fun getSeasonDetails(tvId: Int, seasonNumber: Int): Resource<SeasonData>

    suspend fun saveWatchlist(watchlist: Boolean, tvShowEntity: TvShowEntity): TvShowEntity?
    suspend fun saveWatched(watched: Boolean, tvShowEntity: TvShowEntity): TvShowEntity?

}