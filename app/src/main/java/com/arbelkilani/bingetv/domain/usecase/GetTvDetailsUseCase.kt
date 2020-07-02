package com.arbelkilani.bingetv.domain.usecase

import com.arbelkilani.bingetv.data.model.tv.TvShow
import com.arbelkilani.bingetv.domain.repositories.TvShowRepository

class GetTvDetailsUseCase(private val tvShowRepository: TvShowRepository) {
    suspend operator fun invoke(id: Int) = tvShowRepository.getTvDetails(id)
    suspend fun saveToWatchlist(tv: TvShow) = tvShowRepository.saveToWatchlist(tv)
    suspend fun setTvShowWatched(tv: TvShow) = tvShowRepository.setTvShowWatched(tv)
}