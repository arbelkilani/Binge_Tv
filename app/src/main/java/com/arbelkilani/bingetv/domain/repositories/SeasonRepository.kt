package com.arbelkilani.bingetv.domain.repositories

import com.arbelkilani.bingetv.data.entities.base.Resource
import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity

interface SeasonRepository {
    suspend fun saveWatched(
        watched: Boolean,
        seasonEntity: SeasonEntity,
        tvShowEntity: TvShowEntity
    ): SeasonEntity?

    suspend fun getSeasonDetails(
        tvShowEntity: TvShowEntity,
        seasonEntity: SeasonEntity
    ): Resource<SeasonEntity>
}