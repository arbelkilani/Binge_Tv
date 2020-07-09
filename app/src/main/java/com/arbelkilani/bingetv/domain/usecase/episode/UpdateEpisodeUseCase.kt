package com.arbelkilani.bingetv.domain.usecase.episode

import com.arbelkilani.bingetv.domain.entities.episode.EpisodeEntity
import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.domain.repositories.EpisodeRepository

class UpdateEpisodeUseCase(private val episodeRepository: EpisodeRepository) {
    suspend fun saveWatched(
        watched: Boolean,
        episodeEntity: EpisodeEntity,
        tvShowEntity: TvShowEntity,
        seasonEntity: SeasonEntity
    ): EpisodeEntity? =
        episodeRepository.saveWatched(watched, episodeEntity, tvShowEntity, seasonEntity)
}
