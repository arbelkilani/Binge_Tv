package com.arbelkilani.bingetv.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.domain.usecase.OnTheAirUseCase
import com.arbelkilani.bingetv.domain.usecase.PopularUseCase
import com.arbelkilani.bingetv.presentation.adapters.OnTheAirAdapter
import com.arbelkilani.bingetv.presentation.adapters.PopularAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListAllTvShowViewModel(
    private val extraTag: String,
    private val popularUseCase: PopularUseCase,
    private val onTheAirUseCase: OnTheAirUseCase
) : BaseViewModel() {

    private var popularJob: Job? = null
    private var onTheAirJob: Job? = null

    private val _tvShowPagingData = MutableLiveData<PagingData<TvShowEntity>>()
    val tvShowPagingData: LiveData<PagingData<TvShowEntity>>
        get() = _tvShowPagingData

    private val _toolbarTitle = MutableLiveData<String>()
    val toolbarTitle: LiveData<String>
        get() = _toolbarTitle

    init {


        scope.launch(Dispatchers.IO) {
            when (extraTag) {
                PopularAdapter::class.java.simpleName -> {
                    getPopular()
                    _toolbarTitle.postValue("Popular")
                }

                OnTheAirAdapter::class.java.simpleName -> {
                    getOnTheAir()
                    _toolbarTitle.postValue("On the air")
                }
            }
        }
    }

    private fun getPopular() {
        popularJob?.cancel()
        popularJob = scope.launch {
            popularUseCase.invoke()
                .catch { cause -> Log.i("TAG", "e = ${cause.localizedMessage}") }
                .collect {
                    _tvShowPagingData.postValue(it)
                }
        }
    }

    private fun getOnTheAir() {
        onTheAirJob?.cancel()
        onTheAirJob = scope.launch {
            onTheAirUseCase.invoke()
                .catch { cause -> Log.i("TAG", "e = ${cause.localizedMessage}") }
                .collect {
                    _tvShowPagingData.postValue(it)
                }
        }
    }
}