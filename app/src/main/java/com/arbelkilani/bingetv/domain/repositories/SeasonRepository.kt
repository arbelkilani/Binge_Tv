package com.arbelkilani.bingetv.domain.repositories

import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity

interface SeasonRepository {
    suspend fun saveWatched(
        watched: Boolean,
        seasonEntity: SeasonEntity,
        tvShowId: Int
    ): SeasonEntity?
}