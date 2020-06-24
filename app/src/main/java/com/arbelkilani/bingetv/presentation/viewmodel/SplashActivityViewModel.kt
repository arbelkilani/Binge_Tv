package com.arbelkilani.bingetv.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.tv.CombinedObjects
import com.arbelkilani.bingetv.domain.usecase.GetAiringTodayUseCase
import com.arbelkilani.bingetv.domain.usecase.GetTrendingTvUseCase
import com.arbelkilani.bingetv.utils.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SplashActivityViewModel constructor(
    private val getAiringTodayUseCase: GetAiringTodayUseCase,
    private val getTrendingTvUseCase: GetTrendingTvUseCase
) :
    BaseViewModel() {

    private val TAG = SplashActivityViewModel::class.java.simpleName

    val resource = MutableLiveData<Resource<CombinedObjects>>()

    init {
        fetchData()
    }

    private fun fetchData() {

        //TODO check how to zip more than two flows

        scope.launch {
            delay(Constants.SPLASH_DELAY) // set delay for the splash
            getAiringTodayUseCase.invoke()
                .zip(getAiringTodayUseCase.invoke()) //TODO remove after testing paging
                { airingToday, trending ->
                    return@zip CombinedObjects(airingToday, trending)
                }
                .catch { cause: Throwable ->
                    Log.i(TAG, "cause = ${cause.localizedMessage}")
                    resource.postValue(Resource.exception(cause, null))
                }
                .collect { value ->
                    resource.postValue(Resource.success(value))
                }
        }
    }
}