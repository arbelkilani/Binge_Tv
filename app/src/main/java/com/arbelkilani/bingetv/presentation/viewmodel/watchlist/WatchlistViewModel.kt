package com.arbelkilani.bingetv.presentation.viewmodel.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.domain.usecase.RecommendationsUseCase
import com.arbelkilani.bingetv.domain.usecase.tv.GetWatchListUseCase
import com.arbelkilani.bingetv.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WatchlistViewModel(
    private val getWatchListUseCase: GetWatchListUseCase,
    private val recommendationsUseCase: RecommendationsUseCase
) : BaseViewModel() {

    companion object {
        private const val TAG = "WatchlistViewModel"
    }

    private var _watchlist = MutableLiveData<List<TvShowEntity>>()
    val watchlist: LiveData<List<TvShowEntity>>
        get() = _watchlist

    fun refreshWatchlist() {
        watchlist()
    }

    private fun watchlist() {
        scope.launch(Dispatchers.IO) {
            _watchlist.postValue(getWatchListUseCase.invoke())
        }
    }

    suspend fun recommendations(position: Int): Flow<PagingData<TvShowEntity>> {
        val id = _watchlist.value?.get(position)?.id!!
        return recommendationsUseCase.invoke(id).cachedIn(viewModelScope)
    }
}