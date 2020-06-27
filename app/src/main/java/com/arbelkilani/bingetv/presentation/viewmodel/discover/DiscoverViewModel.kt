package com.arbelkilani.bingetv.presentation.viewmodel.discover

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arbelkilani.bingetv.data.model.tv.CombinedObjects
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.domain.usecase.TestUseCase
import com.arbelkilani.bingetv.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.Flow

class DiscoverViewModel(
    data: CombinedObjects,
    private val testUseCase: TestUseCase
) : BaseViewModel() {

    companion object {
        private const val TAG = "DiscoverViewModel"
    }

    private val _combined = MutableLiveData<CombinedObjects>(data)

    val trending = Transformations.map(_combined) {
        _combined.value!!.trending.results
    }

    private var currentResult: Flow<PagingData<Tv>>? = null

    init {
        Log.i(TAG, "init")
    }

    suspend fun getTvShows(): Flow<PagingData<Tv>> {
        val lastResult = currentResult
        val newResult = testUseCase.invoke().cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }

}