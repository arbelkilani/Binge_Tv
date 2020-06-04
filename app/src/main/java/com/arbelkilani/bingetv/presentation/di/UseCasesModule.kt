package com.arbelkilani.bingetv.presentation.di

import com.arbelkilani.bingetv.domain.repositories.AiringTodayRepository
import com.arbelkilani.bingetv.domain.repositories.GenresRepository
import com.arbelkilani.bingetv.domain.usecase.GetAiringTodayUseCase
import com.arbelkilani.bingetv.domain.usecase.GetGenreUseCase
import org.koin.dsl.module

val UseCasesModule = module {

    single { createGetGenreUseCase(get()) }

    single { createAiringTodayUseCase(get()) }

}


fun createGetGenreUseCase(genresRepository: GenresRepository): GetGenreUseCase {
    return GetGenreUseCase(genresRepository)
}

fun createAiringTodayUseCase(airingTodayRepository: AiringTodayRepository): GetAiringTodayUseCase {
    return GetAiringTodayUseCase(airingTodayRepository)
}