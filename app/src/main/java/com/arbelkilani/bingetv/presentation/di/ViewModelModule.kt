package com.arbelkilani.bingetv.presentation.di

import com.arbelkilani.bingetv.presentation.viewmodel.DetailsTvActivityViewModel
import com.arbelkilani.bingetv.presentation.viewmodel.SplashActivityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val ViewModelModule = module {
    viewModel { SplashActivityViewModel(get(), get()) }
    viewModel { DetailsTvActivityViewModel(get(), get()) }
}


