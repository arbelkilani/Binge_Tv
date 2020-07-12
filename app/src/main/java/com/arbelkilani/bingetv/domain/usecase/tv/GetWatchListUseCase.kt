package com.arbelkilani.bingetv.domain.usecase.tv

import com.arbelkilani.bingetv.domain.repositories.TvShowRepository

class GetWatchListUseCase(private val tvShowRepository: TvShowRepository) {
    suspend operator fun invoke() = tvShowRepository.watchlist()
}