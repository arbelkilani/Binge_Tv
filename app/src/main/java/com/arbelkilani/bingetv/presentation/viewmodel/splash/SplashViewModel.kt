package com.arbelkilani.bingetv.presentation.viewmodel.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.bingetv.domain.usecase.DiscoverUseCase
import com.arbelkilani.bingetv.domain.usecase.TrendingUseCase
import com.arbelkilani.bingetv.presentation.viewmodel.BaseViewModel
import com.arbelkilani.bingetv.utils.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SplashViewModel constructor(
    private val trendingUseCase: TrendingUseCase,
    private val discoverUseCase: DiscoverUseCase
) :
    BaseViewModel() {

    private val _resource = MutableLiveData<Boolean>()
    val resource: LiveData<Boolean>
        get() = _resource

    companion object {
        const val TAG = "SplashViewModel"
    }

    init {
        Log.i(TAG, "init")
        fetchData()
    }

    private fun fetchData() {

        scope.launch {
            delay(Constants.SPLASH_DELAY)
            _resource.postValue(true)
        }
    }
}