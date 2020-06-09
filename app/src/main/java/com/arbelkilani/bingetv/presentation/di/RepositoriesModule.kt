package com.arbelkilani.bingetv.presentation.di

import com.arbelkilani.bingetv.data.repositories.genre.GenresRepositoryImp
import com.arbelkilani.bingetv.data.repositories.tv.TvShowRepositoryImp
import com.arbelkilani.bingetv.data.source.local.genre.GenreDao
import com.arbelkilani.bingetv.data.source.remote.ApiService
import com.arbelkilani.bingetv.domain.repositories.TvShowRepository
import com.arbelkilani.bingetv.domain.repositories.GenresRepository
import org.koin.dsl.module

val RepositoriesModule = module {

    single { createGenreRepository(get(), get()) }

    single { createAiringTodayRepository(get()) }
}

fun createGenreRepository(
    apiService: ApiService,
    genreDao: GenreDao
): GenresRepository {
    return GenresRepositoryImp(
        apiService,
        genreDao
    )
}

fun createAiringTodayRepository(
    apiService: ApiService
): TvShowRepository {
    return TvShowRepositoryImp(
        apiService
    )
}