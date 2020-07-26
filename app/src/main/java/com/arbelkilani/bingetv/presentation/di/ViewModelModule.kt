package com.arbelkilani.bingetv.presentation.di

import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.presentation.viewmodel.ListAllTvShowViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.SearchViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.TvDetailsActivityViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.discover.DiscoverViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.profile.ProfileViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.season.SeasonDetailsViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.splash.SplashViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.watched.WatchedViewModel
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
    viewModel { DiscoverViewModel(get(), get(), get()) }
    viewModel { (seasonEntity: SeasonEntity, tvShowEntity: TvShowEntity) ->
        SeasonDetailsViewModel(
            seasonEntity,
            tvShowEntity,
            get(),
            get()
        )
    }
    viewModel { WatchlistViewModel(get(), get()) }
    viewModel { WatchedViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { (tag: String) -> ListAllTvShowViewModel(tag, get(), get()) }

}


