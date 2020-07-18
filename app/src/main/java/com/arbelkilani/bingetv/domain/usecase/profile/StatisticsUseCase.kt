package com.arbelkilani.bingetv.domain.usecase.profile

import com.arbelkilani.bingetv.domain.repositories.ProfileRepository

class StatisticsUseCase(private val profileRepository: ProfileRepository) {
    suspend fun getWatchedEpisodesCount() = profileRepository.getWatchedEpisodesCount()
}