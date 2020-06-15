package com.arbelkilani.bingetv.domain.usecase

import com.arbelkilani.bingetv.domain.repositories.TvShowRepository
import kotlinx.coroutines.flow.flow

class GetTrendingTvUseCase(private val tvShowRepository: TvShowRepository) {
    suspend operator fun invoke() = tvShowRepository.getTrendingTv()
}
