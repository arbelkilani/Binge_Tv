package com.arbelkilani.bingetv.domain.repositories

interface ProfileRepository {

    suspend fun getWatchedEpisodesCount(): Int
}