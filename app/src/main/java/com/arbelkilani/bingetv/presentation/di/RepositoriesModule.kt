package com.arbelkilani.bingetv.presentation.di

import com.arbelkilani.bingetv.data.repositories.tv.TvShowRepositoryImp
import com.arbelkilani.bingetv.data.source.local.season.SeasonDao
import com.arbelkilani.bingetv.data.source.local.tv.TvDao
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTmdbService
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTvMazeService
import com.arbelkilani.bingetv.domain.repositories.TvShowRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@FlowPreview
val RepositoriesModule = module {

    single { createTvShowRepository(get(), get(), get(), get()) }
}

@ExperimentalCoroutinesApi
@FlowPreview
fun createTvShowRepository(
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