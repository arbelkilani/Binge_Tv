package com.arbelkilani.bingetv.presentation.di

import com.arbelkilani.bingetv.domain.repositories.TvShowRepository
import com.arbelkilani.bingetv.domain.repositories.GenresRepository
import com.arbelkilani.bingetv.domain.usecase.*
import org.koin.dsl.module

val UseCasesModule = module {

    single { createGetGenreUseCase(get()) }

    single { createAiringTodayUseCase(get()) }

    single { createTrendingTvUseCase(get()) }

    single { createTvDetailsUseCase(get()) }

    single { createGetCreditsUseCase(get()) }

}


fun createGetGenreUseCase(genresRepository: GenresRepository): GetGenreUseCase {
    return GetGenreUseCase(genresRepository)
}

fun createAiringTodayUseCase(tvShowRepository: TvShowRepository): GetAiringTodayUseCase {
    return GetAiringTodayUseCase(tvShowRepository)
}

fun createTrendingTvUseCase(tvShowRepository: TvShowRepository): GetTrendingTvUseCase {
    return GetTrendingTvUseCase(tvShowRepository)
}

fun createTvDetailsUseCase(tvShowRepository: TvShowRepository): GetTvDetailsUseCase {
    return GetTvDetailsUseCase(tvShowRepository)
}

fun createGetCreditsUseCase(tvShowRepository: TvShowRepository): GetCreditsUseCase {
    return GetCreditsUseCase(tvShowRepository)
}