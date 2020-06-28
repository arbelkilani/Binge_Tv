package com.arbelkilani.bingetv.presentation.viewmodel.discover

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.domain.usecase.DiscoverUseCase
import com.arbelkilani.bingetv.domain.usecase.TrendingUseCase
import com.arbelkilani.bingetv.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.Flow

class DiscoverViewModel(
    private val trendingUseCase: TrendingUseCase,
    private val discoverUseCase: DiscoverUseCase
) : BaseViewModel() {

    companion object {
        private const val TAG = "DiscoverViewModel"
    }

    init {
        Log.i(TAG, "init")
    }

    suspend fun getTrending(): Flow<ApiResponse<Tv>> {
        return trendingUseCase.invoke()
    }

    suspend fun getDiscover(): Flow<PagingData<Tv>> {
        return discoverUseCase.invoke().cachedIn(viewModelScope)
    }

}