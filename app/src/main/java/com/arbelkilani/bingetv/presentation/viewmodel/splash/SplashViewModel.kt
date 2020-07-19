package com.arbelkilani.bingetv.presentation.viewmodel.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.bingetv.domain.usecase.SaveGenreUseCase
import com.arbelkilani.bingetv.presentation.viewmodel.BaseViewModel
import com.arbelkilani.bingetv.utils.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SplashViewModel constructor(
    private val saveGenreUseCase: SaveGenreUseCase
) :
    BaseViewModel() {

    companion object {
        const val TAG = "SplashViewModel"
    }

    private val _state = MutableLiveData<Boolean>(false)
    val state: LiveData<Boolean>
        get() = _state

    init {
        fetchData()
    }

    private fun fetchData() {
        scope.launch {
            saveGenreUseCase.invoke().let {
                _state.postValue(true)
            }
            delay(Constants.SPLASH_DELAY)
        }
    }
}