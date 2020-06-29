package com.arbelkilani.bingetv.domain.usecase

import com.arbelkilani.bingetv.domain.repositories.TvShowRepository

class SeasonDetailsUseCase(private val tvShowRepository: TvShowRepository) {
    suspend operator fun invoke(tvId: Int, seasonNumber: Int) =
        tvShowRepository.getSeasonDetails(tvId, seasonNumber)
}