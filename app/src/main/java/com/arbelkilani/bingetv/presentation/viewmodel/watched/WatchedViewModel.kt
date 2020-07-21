package com.arbelkilani.bingetv.presentation.viewmodel.watched

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.domain.usecase.tv.WatchedUseCase
import com.arbelkilani.bingetv.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class WatchedViewModel(
    private val watchedUseCase: WatchedUseCase
) : BaseViewModel() {

    private val _returningSeries = MutableLiveData<List<TvShowEntity>>()
    val returningSeries: LiveData<List<TvShowEntity>>
        get() = _returningSeries

    fun refresh() {
        scope.launch {

            val tvShows = watchedUseCase.watched()
            _returningSeries.postValue(tvShows.filter { it.inProduction })
        }
    }

}