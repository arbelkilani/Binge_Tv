package com.arbelkilani.bingetv.domain.usecase.profile

import android.content.Intent
import com.arbelkilani.bingetv.domain.repositories.ProfileRepository
import com.google.firebase.auth.FirebaseUser

class ProfileUseCase(private val profileRepository: ProfileRepository) {
    fun isConnected() = profileRepository.isConnected()
    fun getSignedInAccountFromIntent(data: Intent?) =
        profileRepository.getSignedInAccountFromIntent(data)

    fun saveUser(user: FirebaseUser?) = profileRepository.saveUser(user)
}