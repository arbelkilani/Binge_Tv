package com.arbelkilani.bingetv.domain.usecase

import com.arbelkilani.bingetv.domain.repositories.GenresRepository

class SaveGenreUseCase(
    private val genresRepository: GenresRepository
) {
    suspend operator fun invoke() = genresRepository.saveGenres()
}