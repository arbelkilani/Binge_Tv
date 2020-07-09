package com.arbelkilani.bingetv.presentation.di

import com.arbelkilani.bingetv.data.repositories.episode.EpisodeRepositoryImp
import com.arbelkilani.bingetv.data.repositories.season.SeasonRepositoryImp
import com.arbelkilani.bingetv.data.repositories.tv.TvShowRepositoryImp
import com.arbelkilani.bingetv.data.source.local.episode.EpisodeDao
import com.arbelkilani.bingetv.data.source.local.season.SeasonDao
import com.arbelkilani.bingetv.data.source.local.tv.TvDao
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTmdbService
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTvMazeService
import com.arbelkilani.bingetv.domain.repositories.EpisodeRepository
import com.arbelkilani.bingetv.domain.repositories.SeasonRepository
import com.arbelkilani.bingetv.domain.repositories.TvShowRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@FlowPreview
val RepositoriesModule = module {

    single { tvShowRepository(get(), get(), get(), get()) }

    single { seasonRepository(get(), get(), get()) }

    single { episodeRepository(get(), get()) }
}

fun episodeRepository(
    episodeDao: EpisodeDao,
    seasonDao: SeasonDao
): EpisodeRepository {
    return EpisodeRepositoryImp(episodeDao, seasonDao)
}

fun seasonRepository(
    apiTmdbService: ApiTmdbService,
    seasonDao: SeasonDao,
    episodeDao: EpisodeDao
): SeasonRepository {
    return SeasonRepositoryImp(apiTmdbService, seasonDao, episodeDao)
}

@ExperimentalCoroutinesApi
@FlowPreview
fun tvShowRepository(
    apiTmdbService: ApiTmdbService,
    apiTvMazeService: ApiTvMazeService,
    tvDao: TvDao,
    seasonDao: SeasonDao
): TvShowRepository {
    return TvShowRepositoryImp(
        apiTmdbService,
        apiTvMazeService,
        tvDao,
        seasonDao
    )
}