package com.arbelkilani.bingetv.domain.repositories

import android.content.Intent
import com.arbelkilani.bingetv.domain.entities.genre.GenreEntity
import com.arbelkilani.bingetv.domain.entities.profile.StatisticsEntity
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface ProfileRepository {

    suspend fun getStatistics(): StatisticsEntity
    suspend fun getGenres(): List<GenreEntity>
    fun isConnected(): Boolean
    fun getSignedInAccountFromIntent(data: Intent?): AuthCredential?
    suspend fun saveUser(user: FirebaseUser?)
}