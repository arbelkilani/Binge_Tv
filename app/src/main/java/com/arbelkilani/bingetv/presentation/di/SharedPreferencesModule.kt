package com.arbelkilani.bingetv.presentation.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val SharedPreferencesModule = module {
    single(named("settingsPrefs")) {
        provideSettingsPreferences(
            androidApplication()
        )
    }
    single(named("securePrefs")) {
        provideSecurePreferences(
            androidApplication()
        )
    }
}

private const val PREFERENCES_FILE_KEY = "com.arbelkilani.bingetv.settings_preferences"
private const val SECURE_PREFS_FILE_KEY = "com.arbelkilani.bingetv.secure_preferences"

private fun provideSettingsPreferences(app: Application): SharedPreferences =
    app.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)

private fun provideSecurePreferences(app: Application): SharedPreferences =
    app.getSharedPreferences(SECURE_PREFS_FILE_KEY, Context.MODE_PRIVATE)


// viewModel { MainViewModel(get(named("settingsPrefs"))) }

// private val preferences: SharedPreferences by inject(named("settingsPrefs"))