package com.arbelkilani.bingetv.presentation.viewmodel.discover

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.domain.usecase.OnTheAirUseCase
import com.arbelkilani.bingetv.domain.usecase.TrendingUseCase
import com.arbelkilani.bingetv.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.Flow

class DiscoverViewModel(
    private val trendingUseCase: TrendingUseCase,
    private val onTheAirUseCase: OnTheAirUseCase
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

    suspend fun getOnTheAir(): Flow<PagingData<Tv>> {
        return onTheAirUseCase.invoke().cachedIn(viewModelScope)
    }

}