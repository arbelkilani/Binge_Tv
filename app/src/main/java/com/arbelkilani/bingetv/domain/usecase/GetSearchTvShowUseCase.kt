package com.arbelkilani.bingetv.domain.usecase

import com.arbelkilani.bingetv.domain.repositories.TvShowRepository

class GetSearchTvShowUseCase(private val tvShowRepository: TvShowRepository) {
    suspend operator fun invoke(query: String) = tvShowRepository.searchTvShow(query)
}
