package com.arbelkilani.bingetv.domain.usecase.tv

import com.arbelkilani.bingetv.domain.repositories.TvShowRepository

class WatchedUseCase(private val tvShowRepository: TvShowRepository) {
    suspend fun watched() = tvShowRepository.watched()
}