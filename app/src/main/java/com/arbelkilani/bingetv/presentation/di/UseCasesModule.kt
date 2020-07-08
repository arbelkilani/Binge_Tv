package com.arbelkilani.bingetv.presentation.di

import com.arbelkilani.bingetv.domain.repositories.GenresRepository
import com.arbelkilani.bingetv.domain.repositories.SeasonRepository
import com.arbelkilani.bingetv.domain.repositories.TvShowRepository
import com.arbelkilani.bingetv.domain.usecase.*
import com.arbelkilani.bingetv.domain.usecase.season.GetSeasonDetailsUseCase
import com.arbelkilani.bingetv.domain.usecase.season.UpdateSeasonUseCase
import com.arbelkilani.bingetv.domain.usecase.tv.UpdateTvShowUseCase
import org.koin.dsl.module

val UseCasesModule = module {

    single { createDiscoverUseCase(get()) }

    single { createTrendingUseCase(get()) }

    single { createAiringTodayUseCase(get()) }

    single { createPopularUseCase(get()) }

    single { createOnTheAirUseCase(get()) }

    single { createGetGenreUseCase(get()) }

    single { createTvDetailsUseCase(get()) }



    single { createGetCreditsUseCase(get()) }

    single { createGetNextEpisodeDataUseCase(get()) }

    single { createGetSearchTvShowUseCase(get()) }

    single { updateTvShowUseCase(get()) }

    // season
    single { getSeasonDetailsUseCase(get()) }
    single { updateSeasonUseCase(get()) }
}

fun createDiscoverUseCase(tvShowRepository: TvShowRepository): DiscoverUseCase {
    return DiscoverUseCase(tvShowRepository)
}

fun createTrendingUseCase(tvShowRepository: TvShowRepository): TrendingUseCase {
    return TrendingUseCase(tvShowRepository)
}

fun createAiringTodayUseCase(tvShowRepository: TvShowRepository): AiringTodayUseCase {
    return AiringTodayUseCase(tvShowRepository)
}

fun createPopularUseCase(tvShowRepository: TvShowRepository): PopularUseCase {
    return PopularUseCase(tvShowRepository)
}

fun createOnTheAirUseCase(tvShowRepository: TvShowRepository): OnTheAirUseCase {
    return OnTheAirUseCase(tvShowRepository)
}

fun createGetGenreUseCase(genresRepository: GenresRepository): GetGenreUseCase {
    return GetGenreUseCase(genresRepository)
}

fun createTvDetailsUseCase(tvShowRepository: TvShowRepository): GetTvDetailsUseCase {
    return GetTvDetailsUseCase(tvShowRepository)
}


fun createGetCreditsUseCase(tvShowRepository: TvShowRepository): GetCreditsUseCase {
    return GetCreditsUseCase(tvShowRepository)
}

fun createGetNextEpisodeDataUseCase(tvShowRepository: TvShowRepository): GetNextEpisodeDataUseCase {
    return GetNextEpisodeDataUseCase(tvShowRepository)
}

fun createGetSearchTvShowUseCase(tvShowRepository: TvShowRepository): SearchUseCase {
    return SearchUseCase(tvShowRepository)
}

fun updateTvShowUseCase(tvShowRepository: TvShowRepository): UpdateTvShowUseCase {
    return UpdateTvShowUseCase(tvShowRepository)
}

fun getSeasonDetailsUseCase(seasonRepository: SeasonRepository): GetSeasonDetailsUseCase {
    return GetSeasonDetailsUseCase(seasonRepository)
}

fun updateSeasonUseCase(seasonRepository: SeasonRepository): UpdateSeasonUseCase {
    return UpdateSeasonUseCase(seasonRepository)
}
