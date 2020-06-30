package com.arbelkilani.bingetv.domain.usecase

import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.domain.repositories.TvShowRepository

class GetTvDetailsUseCase(private val tvShowRepository: TvShowRepository) {
    suspend operator fun invoke(id: Int) = tvShowRepository.getTvDetails(id)
    suspend fun saveToWatchlist(tv: Tv) = tvShowRepository.saveToWatchlist(tv)
    suspend fun setTvShowWatched(tv: Tv) = tvShowRepository.setTvShowWatched(tv)
}