package com.arbelkilani.bingetv.domain.repositories

import com.arbelkilani.bingetv.domain.entities.genre.GenreEntity
import com.arbelkilani.bingetv.domain.entities.profile.StatisticsEntity

interface ProfileRepository {

    suspend fun getStatistics(): StatisticsEntity
    suspend fun getGenres(): List<GenreEntity>
}