package com.arbelkilani.bingetv.presentation.di

import com.arbelkilani.bingetv.data.model.base.ResponseHandler
import com.arbelkilani.bingetv.data.repositories.genre.GenresRepositoryImp
import com.arbelkilani.bingetv.data.repositories.tv.AiringTodayRepositoryImp
import com.arbelkilani.bingetv.data.source.local.genre.GenreDao
import com.arbelkilani.bingetv.data.source.remote.ApiService
import com.arbelkilani.bingetv.domain.repositories.AiringTodayRepository
import com.arbelkilani.bingetv.domain.repositories.GenresRepository
import org.koin.dsl.module

val RepositoriesModule = module {

    single { createGenreRepository(get(), get(), ResponseHandler()) }

    single { createAiringTodayRepository(get(), ResponseHandler()) }
}

fun createGenreRepository(
    apiService: ApiService,
    genreDao: GenreDao,
    responseHandler: ResponseHandler
): GenresRepository {
    return GenresRepositoryImp(
        apiService,
        genreDao,
        responseHandler
    )
}

fun createAiringTodayRepository(
    apiService: ApiService,
    responseHandler: ResponseHandler
): AiringTodayRepository {
    return AiringTodayRepositoryImp(
        apiService,
        responseHandler
    )
}