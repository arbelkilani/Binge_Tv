package com.arbelkilani.bingetv.presentation.di

import com.arbelkilani.bingetv.data.model.season.Season
import com.arbelkilani.bingetv.presentation.viewmodel.DetailsTvActivityViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.SearchViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.discover.DiscoverViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.season.SeasonDetailsViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.splash.SplashViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


@ExperimentalCoroutinesApi
val ViewModelModule = module {
    viewModel { SplashViewModel(get(), get()) }
    viewModel { DetailsTvActivityViewModel(get(), get(), get()) }
    viewModel { SearchViewModel(get()) }
    //viewModel { (combined: CombinedObjects) -> DiscoverViewModel(combined, get()) }
    viewModel { DiscoverViewModel(get(), get()) }
    viewModel { (tvId: Int, season: Season) -> SeasonDetailsViewModel(tvId, season, get()) }

}


