package com.arbelkilani.bingetv.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.base.Status
import com.arbelkilani.bingetv.data.model.genre.Genre
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.domain.usecase.GetAiringTodayUseCase
import com.arbelkilani.bingetv.domain.usecase.GetGenreUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivityViewModel constructor(
    private val getGenreUseCase: GetGenreUseCase,
    private val getAiringTodayUseCase: GetAiringTodayUseCase
) :
    BaseViewModel() {

    private val TAG = SplashActivityViewModel::class.java.simpleName

    private val genres = MutableLiveData<Resource<List<Genre>>>()
    val airingToday = MutableLiveData<Resource<ApiResponse<Tv>>>()

    val status: MutableLiveData<Status> by lazy {
        MutableLiveData<Status>()
    }

    fun fetchData() {

        scope.launch {

            val genresAsync = async(Dispatchers.IO) { getGenreUseCase.invoke() }
            val airingTodayAsync = async(Dispatchers.IO) { getAiringTodayUseCase.invoke() }
            val delay = async { delay(3000) }

            delay.await()
            genres.value = genresAsync.await()
            airingToday.value = airingTodayAsync.await()

            if (genres.value!!.status == Status.SUCCESS && airingToday.value!!.status == Status.SUCCESS) {
                status.value = Status.SUCCESS
            } else {
                status.value = Status.ERROR
            }
        }
    }
}