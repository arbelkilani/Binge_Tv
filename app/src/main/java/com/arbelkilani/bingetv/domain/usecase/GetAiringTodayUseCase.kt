package com.arbelkilani.bingetv.domain.usecase

import com.arbelkilani.bingetv.domain.repositories.AiringTodayRepository

class GetAiringTodayUseCase(
    private val getAiringTodayRepository: AiringTodayRepository
) {
    suspend operator fun invoke() = getAiringTodayRepository.getAiringToday()
}