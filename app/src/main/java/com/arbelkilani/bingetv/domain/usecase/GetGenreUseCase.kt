package com.arbelkilani.bingetv.domain.usecase

import com.arbelkilani.bingetv.domain.repositories.GenresRepository

class GetGenreUseCase(
    private val genresRepository: GenresRepository
) {
    suspend operator fun invoke() = genresRepository.getGenres()
}