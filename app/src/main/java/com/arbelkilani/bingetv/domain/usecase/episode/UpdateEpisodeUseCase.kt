package com.arbelkilani.bingetv.domain.usecase.episode

import com.arbelkilani.bingetv.domain.entities.episode.EpisodeEntity
import com.arbelkilani.bingetv.domain.repositories.EpisodeRepository

class UpdateEpisodeUseCase(private val episodeRepository: EpisodeRepository) {
    suspend fun saveWatched(
        watched: Boolean,
        episodeEntity: EpisodeEntity,
        tvShowId: Int,
        seasonId: Int
    ): EpisodeEntity? =
        episodeRepository.saveWatched(watched, episodeEntity, tvShowId, seasonId)
}
