package com.arbelkilani.bingetv.domain.usecase

import com.arbelkilani.bingetv.data.model.tv.TvShow
import com.arbelkilani.bingetv.domain.repositories.TvShowRepository

class GetTvDetailsUseCase(private val tvShowRepository: TvShowRepository) {
    suspend operator fun invoke(id: Int) = tvShowRepository.getTvDetails(id)
    suspend fun saveWatchlist(tv: TvShow) = tvShowRepository.saveWatchlist(tv)
    suspend fun saveWatched(tv: TvShow) = tvShowRepository.saveWatched(tv)
}