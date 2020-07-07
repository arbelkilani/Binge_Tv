package com.arbelkilani.bingetv.domain.usecase.tv

import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.domain.repositories.TvShowRepository

class UpdateTvShowUseCase(private val tvShowRepository: TvShowRepository) {
    suspend fun saveWatched(tvShowEntity: TvShowEntity) =
        tvShowRepository.saveWatched(tvShowEntity)
}
