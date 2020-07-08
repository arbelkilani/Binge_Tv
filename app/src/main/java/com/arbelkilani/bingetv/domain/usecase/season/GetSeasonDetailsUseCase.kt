package com.arbelkilani.bingetv.domain.usecase.season

import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.domain.repositories.SeasonRepository

class GetSeasonDetailsUseCase(private val seasonRepository: SeasonRepository) {
    suspend operator fun invoke(
        tvShowEntity: TvShowEntity,
        seasonEntity: SeasonEntity
    ) = seasonRepository.getSeasonDetails(tvShowEntity, seasonEntity)
}