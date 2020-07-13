package com.arbelkilani.bingetv.domain.usecase.season

import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.domain.repositories.SeasonRepository

class UpdateSeasonUseCase(private val seasonRepository: SeasonRepository) {
    suspend fun saveWatched(
        watched: Boolean,
        seasonEntity: SeasonEntity,
        tvShowEntity: TvShowEntity
    ): SeasonEntity? =
        seasonRepository.saveWatched(watched, seasonEntity, tvShowEntity)
}
