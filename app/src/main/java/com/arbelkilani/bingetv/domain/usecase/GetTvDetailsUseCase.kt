package com.arbelkilani.bingetv.domain.usecase

import com.arbelkilani.bingetv.domain.repositories.TvShowRepository

class GetTvDetailsUseCase(private val tvShowRepository: TvShowRepository) {
    suspend operator fun invoke(id: Int) = tvShowRepository.getTvDetails(id)
}