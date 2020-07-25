package com.arbelkilani.bingetv.presentation.di

import com.arbelkilani.bingetv.domain.repositories.*
import com.arbelkilani.bingetv.domain.usecase.*
import com.arbelkilani.bingetv.domain.usecase.episode.UpdateEpisodeUseCase
import com.arbelkilani.bingetv.domain.usecase.profile.StatisticsUseCase
import com.arbelkilani.bingetv.domain.usecase.season.GetSeasonDetailsUseCase
import com.arbelkilani.bingetv.domain.usecase.season.UpdateSeasonUseCase
import com.arbelkilani.bingetv.domain.usecase.tv.GetWatchListUseCase
import com.arbelkilani.bingetv.domain.usecase.tv.UpdateNextEpisodeUseCase
import com.arbelkilani.bingetv.domain.usecase.tv.UpdateTvShowUseCase
import com.arbelkilani.bingetv.domain.usecase.tv.WatchedUseCase
import org.koin.dsl.module

val UseCasesModule = module {

    single { createDiscoverUseCase(get()) }

    single { createTrendingUseCase(get()) }

    single { createAiringTodayUseCase(get()) }

    single { createPopularUseCase(get()) }

    single { createOnTheAirUseCase(get()) }


    single { createTvDetailsUseCase(get()) }



    single { createGetCreditsUseCase(get()) }

    single { createGetNextEpisodeDataUseCase(get()) }

    single { createGetSearchTvShowUseCase(get()) }

    single { updateTvShowUseCase(get()) }

    // tv show
    single { getWatchlistUseCase(get()) }
    single { recommendationsUseCase(get()) }
    single { watchedUseCase(get()) }
    single { updateNextEpisodeUseCase(get()) }

    // season
    single { getSeasonDetailsUseCase(get()) }
    single { updateSeasonUseCase(get()) }

    // episode
    single { updateEpisodeUseCase(get()) }

    // profile
    single { statisticsUseCase(get()) }

    // genre
    single { saveGenreUseCase(get()) }

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

// season
fun getSeasonDetailsUseCase(seasonRepository: SeasonRepository): GetSeasonDetailsUseCase {
    return GetSeasonDetailsUseCase(seasonRepository)
}

fun updateSeasonUseCase(seasonRepository: SeasonRepository): UpdateSeasonUseCase {
    return UpdateSeasonUseCase(seasonRepository)
}

// episode
fun updateEpisodeUseCase(episodeRepository: EpisodeRepository): UpdateEpisodeUseCase {
    return UpdateEpisodeUseCase(episodeRepository)
}

// tv show
fun getWatchlistUseCase(tvShowRepository: TvShowRepository): GetWatchListUseCase {
    return GetWatchListUseCase(tvShowRepository)
}

fun watchedUseCase(tvShowRepository: TvShowRepository): WatchedUseCase {
    return WatchedUseCase(tvShowRepository)
}

fun recommendationsUseCase(tvShowRepository: TvShowRepository): RecommendationsUseCase {
    return RecommendationsUseCase(tvShowRepository)
}

fun updateNextEpisodeUseCase(tvShowRepository: TvShowRepository): UpdateNextEpisodeUseCase {
    return UpdateNextEpisodeUseCase(tvShowRepository)
}

// profile
fun statisticsUseCase(profileRepository: ProfileRepository): StatisticsUseCase {
    return StatisticsUseCase(profileRepository)
}

// genre
fun saveGenreUseCase(genresRepository: GenresRepository): SaveGenreUseCase {
    return SaveGenreUseCase(genresRepository)
}
