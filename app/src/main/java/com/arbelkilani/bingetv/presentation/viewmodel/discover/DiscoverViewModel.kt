package com.arbelkilani.bingetv.presentation.viewmodel.discover

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.arbelkilani.bingetv.data.model.tv.CombinedObjects
import com.arbelkilani.bingetv.domain.usecase.GetRequestMoreUseCase
import com.arbelkilani.bingetv.domain.usecase.TestUseCase
import com.arbelkilani.bingetv.presentation.viewmodel.BaseViewModel

class DiscoverViewModel(
    data: CombinedObjects,
    private val requestMoreUseCase: GetRequestMoreUseCase,
    private val testUseCase: TestUseCase
) : BaseViewModel() {

    companion object {
        private const val TAG = "DiscoverViewModel"
    }

    private val _combined = MutableLiveData<CombinedObjects>().apply {
        postValue(data)
    }

    val airingToday = Transformations.map(_combined) {
        _combined.value!!.airing.results
    }

    val trending = Transformations.map(_combined) {
        _combined.value!!.trending.results
    }

    init {
        Log.i(TAG, "init")
    }

}