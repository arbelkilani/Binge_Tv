package com.arbelkilani.bingetv.domain.repositories

import com.arbelkilani.bingetv.domain.entities.episode.EpisodeEntity

interface EpisodeRepository {
    suspend fun saveWatched(
        watched: Boolean,
        episodeEntity: EpisodeEntity,
        tvShowId: Int,
        seasonId: Int
    ): EpisodeEntity?
}