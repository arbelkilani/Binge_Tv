package com.arbelkilani.bingetv.presentation.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.bingetv.data.enum.HttpStatusCode
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.base.Status
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.domain.usecase.GetAiringTodayUseCase
import com.arbelkilani.bingetv.domain.usecase.GetTrendingTvUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class SplashActivityViewModel constructor(
    private val getAiringTodayUseCase: GetAiringTodayUseCase,
    private val getTrendingTvUseCase: GetTrendingTvUseCase
) :
    BaseViewModel() {

    private val TAG = SplashActivityViewModel::class.java.simpleName

    val airingToday = MutableLiveData<ApiResponse<Tv>>()
    val trendingTv = MutableLiveData<ApiResponse<Tv>>()

    val status: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun fetchData() {

        scope.launch {

            val airingTodayAsync =
                async((Dispatchers.IO)) { getAiringTodayUseCase.invoke() }
            val trendingTvAsync = async((Dispatchers.IO)) { getTrendingTvUseCase.invoke() }

            handleResponse(airingTodayAsync.await(), trendingTvAsync.await())
        }
    }

    private fun handleResponse(
        airingTodayResource: Resource<ApiResponse<Tv>>,
        trendingTvResource: Resource<ApiResponse<Tv>>
    ) {
        airingTodayResource.let { it ->

            when (it.code) {
                HttpStatusCode.SUCCESS -> {
                    status.value = HttpStatusCode.SUCCESS
                    trendingTvResource.let {
                        when (it.code) {
                            HttpStatusCode.SUCCESS -> {
                                airingToday.value = it.data
                                trendingTv.value = it.data
                                status.value = HttpStatusCode.SUCCESS
                            }
                            HttpStatusCode.NETWORK_ERROR -> {
                                Log.i(TAG, "network error airing today")
                                status.value = HttpStatusCode.NETWORK_ERROR
                            }
                            else -> {
                                Log.i(TAG, "other error airing today")
                                status.value = it.code
                            }
                        }
                    }
                }

                HttpStatusCode.NETWORK_ERROR -> {
                    Log.i(TAG, "network error airing today")
                    status.value = HttpStatusCode.NETWORK_ERROR
                }

                else -> {
                    Log.i(TAG, "other error airing today")
                    status.value = it.code
                }
            }
        }


    }
}