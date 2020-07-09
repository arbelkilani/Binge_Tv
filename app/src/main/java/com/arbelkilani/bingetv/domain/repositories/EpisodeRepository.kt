package com.arbelkilani.bingetv.domain.repositories

import com.arbelkilani.bingetv.domain.entities.episode.EpisodeEntity
import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity

interface EpisodeRepository {
    suspend fun saveWatched(
        watched: Boolean,
        episodeEntity: EpisodeEntity,
        tvShowEntity: TvShowEntity,
        seasonEntity: SeasonEntity
    ): EpisodeEntity?
}