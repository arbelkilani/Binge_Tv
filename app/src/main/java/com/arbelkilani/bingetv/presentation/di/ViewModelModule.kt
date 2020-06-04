package com.arbelkilani.bingetv.presentation.di

import com.arbelkilani.bingetv.presentation.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val ViewModelModule = module {
    viewModel { MainActivityViewModel(get(), get()) }
}


