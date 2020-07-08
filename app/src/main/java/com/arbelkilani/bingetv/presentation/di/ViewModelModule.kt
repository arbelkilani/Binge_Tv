package com.arbelkilani.bingetv.presentation.di

import com.arbelkilani.bingetv.data.entities.season.SeasonData
import com.arbelkilani.bingetv.data.entities.tv.TvShowData
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.presentation.viewmodel.SearchViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.TvDetailsActivityViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.discover.DiscoverViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.season.SeasonDetailsViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.splash.SplashViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.watchlist.WatchlistViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


@ExperimentalCoroutinesApi
val ViewModelModule = module {
    viewModel { SplashViewModel(get(), get()) }
    viewModel { (tvShowEntity: TvShowEntity) ->
        TvDetailsActivityViewModel(
            tvShowEntity,
            get(),
            get(),
            get(),
            get()
        )
    }
    viewModel { SearchViewModel(get()) }
    viewModel { DiscoverViewModel(get(), get()) }
    viewModel { (selectedTv: TvShowData, seasonData: SeasonData) ->
        SeasonDetailsViewModel(
            selectedTv,
            seasonData,
            get()
        )
    }
    viewModel { WatchlistViewModel() }

}


