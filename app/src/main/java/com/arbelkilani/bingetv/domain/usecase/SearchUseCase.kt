package com.arbelkilani.bingetv.domain.usecase

import com.arbelkilani.bingetv.domain.repositories.TvShowRepository

class SearchUseCase(private val tvShowRepository: TvShowRepository) {
    suspend operator fun invoke(query: String) = tvShowRepository.search(query)
}
