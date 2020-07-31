package com.arbelkilani.bingetv.presentation.di

import com.arbelkilani.bingetv.domain.repositories.*
import com.arbelkilani.bingetv.domain.usecase.*
import com.arbelkilani.bingetv.domain.usecase.episode.UpdateEpisodeUseCase
import com.arbelkilani.bingetv.domain.usecase.profile.ProfileUseCase
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



    single { createAiringTodayUseCase(get()) }






    single { createTvDetailsUseCase(get()) }



    single { createGetCreditsUseCase(get()) }

    single { createGetNextEpisodeDataUseCase(get()) }

    single { createGetSearchTvShowUseCase(get()) }

    single { updateTvShowUseCase(get()) }

    // tv show
    single { trendingUseCase(get()) }
    single { onTheAirUseCase(get()) }
    single { popularUseCase(get()) }
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
    single { profileUseCase(get()) }

    // genre
    single { saveGenreUseCase(get()) }

}

fun createDiscoverUseCase(tvShowRepository: TvShowRepository): DiscoverUseCase {
    return DiscoverUseCase(tvShowRepository)
}


fun createAiringTodayUseCase(tvShowRepository: TvShowRepository): AiringTodayUseCase {
    return AiringTodayUseCase(tvShowRepository)
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
fun trendingUseCase(tvShowRepository: TvShowRepository): TrendingUseCase {
    return TrendingUseCase(tvShowRepository)
}

fun onTheAirUseCase(tvShowRepository: TvShowRepository): OnTheAirUseCase {
    return OnTheAirUseCase(tvShowRepository)
}

fun popularUseCase(tvShowRepository: TvShowRepository): PopularUseCase {
    return PopularUseCase(tvShowRepository)
}

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

fun profileUseCase(profileRepository: ProfileRepository): ProfileUseCase {
    return ProfileUseCase(profileRepository)
}

// genre
fun saveGenreUseCase(genresRepository: GenresRepository): SaveGenreUseCase {
    return SaveGenreUseCase(genresRepository)
}
