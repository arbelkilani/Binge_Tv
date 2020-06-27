package com.arbelkilani.bingetv.domain.usecase

import com.arbelkilani.bingetv.domain.repositories.TvShowRepository

class DiscoverUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke() = tvShowRepository.getAiringToday()
}