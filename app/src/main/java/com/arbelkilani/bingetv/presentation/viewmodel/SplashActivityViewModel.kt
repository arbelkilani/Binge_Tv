package com.arbelkilani.bingetv.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.bingetv.data.enum.HttpStatusCode
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.base.Status
import com.arbelkilani.bingetv.data.model.tv.CombinedObjects
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.domain.usecase.GetAiringTodayUseCase
import com.arbelkilani.bingetv.domain.usecase.GetTrendingTvUseCase
import com.arbelkilani.bingetv.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

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
                .zip(getTrendingTvUseCase.invoke())
                { airingToday, trending ->
                    return@zip CombinedObjects(airingToday, trending)
                }
                .catch { cause: Throwable ->
                    resource.postValue(Resource.exception(cause, null))
                }
                .collect { value ->
                    resource.postValue(Resource.success(value))
                }
        }
    }
}