package com.arbelkilani.bingetv.presentation.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.genre.Genre
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.domain.usecase.GetAiringTodayUseCase
import com.arbelkilani.bingetv.domain.usecase.GetGenreUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel constructor(
    private val getGenreUseCase: GetGenreUseCase,
    private val getAiringTodayUseCase: GetAiringTodayUseCase
) :
    BaseViewModel() {

    private val TAG = MainActivityViewModel::class.java.simpleName

    private val genres = MutableLiveData<Resource<List<Genre>>>()
    private val airingToday = MutableLiveData<Resource<ApiResponse<Tv>>>()

    val currentName: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun fetchData() {


        scope.launch {
            val genresAsync = async(Dispatchers.IO) { getGenreUseCase.invoke() }
            val airingTodayAsync = async(Dispatchers.IO) { getAiringTodayUseCase.invoke() }
            val delay = async { delay(3000) }

            Log.i(TAG, "genres : ${genresAsync.await()}")
            Log.i(TAG, "airing today : ${airingTodayAsync.await()}")

            Log.i(TAG, "start")
            delay.await()
            genres.value = genresAsync.await()
            airingToday.value = airingTodayAsync.await()

            if (genres.value!!.code == 200 && airingToday.value!!.code == 200) {
                Log.i(TAG, "Success")
                currentName.value = true
            } else {
                Log.e(TAG, "Failure")
                currentName.value = false
            }

            Log.i(TAG, "end")

            //airingToday.value = airingTodayAsync.await()
            //genres.value = genresAsync.await()


        }
    }

    fun getGenres() = liveData {
        val result = getGenreUseCase.invoke()
        emit(result)
    }


    fun getAiringToday() = liveData {
        val result = getAiringTodayUseCase.invoke()
        emit(result)
    }
}