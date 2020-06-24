package com.arbelkilani.bingetv.domain.usecase

import com.arbelkilani.bingetv.domain.repositories.TvShowRepository

class GetRequestMoreUseCase(private val tvShowRepository: TvShowRepository) {
    suspend operator fun invoke() = tvShowRepository.requestMore()
}
