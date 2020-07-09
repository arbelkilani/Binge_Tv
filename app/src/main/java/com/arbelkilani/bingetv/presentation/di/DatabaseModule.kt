package com.arbelkilani.bingetv.presentation.di

import androidx.room.Room
import com.arbelkilani.bingetv.data.source.local.base.BingeTvDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val DatabaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            BingeTvDatabase::class.java, "binge_tv_database"
        ).fallbackToDestructiveMigration().build()
    }

    single { get<BingeTvDatabase>().getTvDao() }

    single { get<BingeTvDatabase>().getSeasonDao() }

    single { get<BingeTvDatabase>().getEpisodeDao() }
}
