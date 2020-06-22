package com.arbelkilani.bingetv.domain.usecase

import com.arbelkilani.bingetv.domain.repositories.TvShowRepository

class GetNextEpisodeDataUseCase(private val tvShowRepository: TvShowRepository) {
    suspend operator fun invoke(id: Int) = tvShowRepository.getNextEpisodeData(id)
}