package com.arbelkilani.bingetv.domain.usecase.tv

import com.arbelkilani.bingetv.domain.repositories.TvShowRepository

class UpdateNextEpisodeUseCase(private val tvShowRepository: TvShowRepository) {
    suspend fun updateNextEpisode() = tvShowRepository.updateNextEpisode()
}