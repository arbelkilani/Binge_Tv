package com.arbelkilani.bingetv.domain.usecase

import com.arbelkilani.bingetv.domain.repositories.TvShowRepository

class OnTheAirUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke() = tvShowRepository.onTheAir()
}