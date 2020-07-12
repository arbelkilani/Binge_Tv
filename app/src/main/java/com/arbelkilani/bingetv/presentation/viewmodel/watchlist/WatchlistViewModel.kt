package com.arbelkilani.bingetv.presentation.viewmodel.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.domain.usecase.tv.GetWatchListUseCase
import com.arbelkilani.bingetv.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WatchlistViewModel(
    private val getWatchListUseCase: GetWatchListUseCase
) : BaseViewModel() {

    companion object {
        private const val TAG = "WatchlistViewModel"
    }

    private var _watchlist = MutableLiveData<List<TvShowEntity>>()
    val watchlist: LiveData<List<TvShowEntity>>
        get() = _watchlist

    init {
        scope.launch(Dispatchers.IO) {
            _watchlist.postValue(getWatchListUseCase.invoke())
        }
    }
}