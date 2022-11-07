package com.arbelkilani.bingetv.domain.usecase.profile

import com.arbelkilani.bingetv.domain.repositories.ProfileRepository

class StatisticsUseCase(private val profileRepository: ProfileRepository) {
    suspend fun getStatistics() = profileRepository.getStatistics()
    suspend fun getGenres() = profileRepository.getGenres()
}