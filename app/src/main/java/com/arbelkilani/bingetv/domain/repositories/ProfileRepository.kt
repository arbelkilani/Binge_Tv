package com.arbelkilani.bingetv.domain.repositories

import com.arbelkilani.bingetv.domain.entities.profile.StatisticsEntity

interface ProfileRepository {

    suspend fun getStatistics(): StatisticsEntity
}