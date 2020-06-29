package com.arbelkilani.bingetv.presentation.di

import com.arbelkilani.bingetv.data.model.season.Season
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.presentation.viewmodel.SearchViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.TvDetailsActivityViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.discover.DiscoverViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.season.SeasonDetailsViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.splash.SplashViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


@ExperimentalCoroutinesApi
val ViewModelModule = module {
    viewModel { SplashViewModel(get(), get()) }
    viewModel { (selectedTv: Tv) -> TvDetailsActivityViewModel(selectedTv, get(), get(), get()) }
    viewModel { SearchViewModel(get()) }
    //viewModel { (combined: CombinedObjects) -> DiscoverViewModel(combined, get()) }
    viewModel { DiscoverViewModel(get(), get()) }
    viewModel { (selectedTv: Tv, season: Season) ->
        SeasonDetailsViewModel(
            selectedTv,
            season,
            get()
        )
    }

}


