package com.arbelkilani.bingetv.domain.usecase

import com.arbelkilani.bingetv.data.entities.tv.TvShowData
import com.arbelkilani.bingetv.domain.repositories.TvShowRepository

class GetTvDetailsUseCase(private val tvShowRepository: TvShowRepository) {
    suspend operator fun invoke(id: Int) = tvShowRepository.getTvDetails(id)
    suspend fun saveWatchlist(tv: TvShowData) = tvShowRepository.saveWatchlist(tv)
    suspend fun saveWatched(tv: TvShowData) = tvShowRepository.saveWatched(tv)
}