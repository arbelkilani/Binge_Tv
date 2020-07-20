package com.arbelkilani.bingetv.presentation.di

import com.arbelkilani.bingetv.data.repositories.episode.EpisodeRepositoryImp
import com.arbelkilani.bingetv.data.repositories.genre.GenreRepositoryImp
import com.arbelkilani.bingetv.data.repositories.profile.ProfileRepositoryImp
import com.arbelkilani.bingetv.data.repositories.season.SeasonRepositoryImp
import com.arbelkilani.bingetv.data.repositories.tv.TvShowRepositoryImp
import com.arbelkilani.bingetv.data.source.local.episode.EpisodeDao
import com.arbelkilani.bingetv.data.source.local.genre.GenreDao
import com.arbelkilani.bingetv.data.source.local.season.SeasonDao
import com.arbelkilani.bingetv.data.source.local.tv.TvDao
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTmdbService
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTvMazeService
import com.arbelkilani.bingetv.domain.repositories.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@FlowPreview
val RepositoriesModule = module {

    single { tvShowRepository(get(), get(), get(), get(), get()) }

    single { seasonRepository(get(), get(), get(), get(), get()) }

    single { episodeRepository(get(), get(), get(), get()) }

    single { profileRepository(get(), get()) }

    single { genreRepository(get(), get()) }
}

fun genreRepository(
    apiTmdbService: ApiTmdbService,
    genreDao: GenreDao
): GenresRepository {
    return GenreRepositoryImp(apiTmdbService, genreDao)
}

fun profileRepository(
    tvDao: TvDao,
    genreDao: GenreDao
): ProfileRepository {
    return ProfileRepositoryImp(tvDao, genreDao)
}

fun episodeRepository(
    episodeDao: EpisodeDao,
    seasonDao: SeasonDao,
    tvDao: TvDao,
    genreDao: GenreDao
): EpisodeRepository {
    return EpisodeRepositoryImp(episodeDao, seasonDao, tvDao, genreDao)
}

fun seasonRepository(
    apiTmdbService: ApiTmdbService,
    seasonDao: SeasonDao,
    episodeDao: EpisodeDao,
    tvDao: TvDao,
    genreDao: GenreDao
): SeasonRepository {
    return SeasonRepositoryImp(apiTmdbService, seasonDao, episodeDao, tvDao, genreDao)
}

@ExperimentalCoroutinesApi
@FlowPreview
fun tvShowRepository(
    apiTmdbService: ApiTmdbService,
    apiTvMazeService: ApiTvMazeService,
    tvDao: TvDao,
    seasonDao: SeasonDao,
    genreDao: GenreDao
): TvShowRepository {
    return TvShowRepositoryImp(
        apiTmdbService,
        apiTvMazeService,
        tvDao,
        seasonDao,
        genreDao
    )
}