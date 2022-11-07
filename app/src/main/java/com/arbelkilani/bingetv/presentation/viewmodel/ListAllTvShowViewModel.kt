package com.arbelkilani.bingetv.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.domain.usecase.OnTheAirUseCase
import com.arbelkilani.bingetv.domain.usecase.PopularUseCase
import com.arbelkilani.bingetv.domain.usecase.RecommendationsUseCase
import com.arbelkilani.bingetv.domain.usecase.tv.WatchedUseCase
import com.arbelkilani.bingetv.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListAllTvShowViewModel(
    private val extraTag: String,
    private val popularUseCase: PopularUseCase,
    private val onTheAirUseCase: OnTheAirUseCase,
    private val watchedUseCase: WatchedUseCase,
    private val recommendationsUseCase: RecommendationsUseCase
) : BaseViewModel() {

    private var popularJob: Job? = null
    private var onTheAirJob: Job? = null

    private val _tvShowPagingData = MutableLiveData<PagingData<TvShowEntity>>()
    val tvShowPagingData: LiveData<PagingData<TvShowEntity>>
        get() = _tvShowPagingData

    private val _tvShowList = MutableLiveData<List<TvShowEntity>>()
    val tvShowList: LiveData<List<TvShowEntity>>
        get() = _tvShowList

    private val _toolbarTitle = MutableLiveData<String>()
    val toolbarTitle: LiveData<String>
        get() = _toolbarTitle

    init {


        scope.launch(Dispatchers.IO) {
            when (extraTag) {
                Constants.POPULAR -> {
                    getPopular()
                    _toolbarTitle.postValue(Constants.POPULAR)
                }

                Constants.ON_THE_AIR -> {
                    getOnTheAir()
                    _toolbarTitle.postValue(Constants.ON_THE_AIR)
                }

                Constants.RETURNING_SERIES -> {
                    getReturning()
                    _toolbarTitle.postValue(Constants.RETURNING_SERIES)
                }

                Constants.ENDED -> {
                    getEnded()
                    _toolbarTitle.postValue(Constants.ENDED)
                }

                Constants.RECOMMENDATIONS -> {
                    getRecommendations()
                    _toolbarTitle.postValue(Constants.RECOMMENDATIONS)
                }
            }
        }
    }

    private fun getReturning() {
        scope.launch(Dispatchers.IO) {
            watchedUseCase.watched()?.let {
                val result = watchedUseCase.watched()?.filter { it.inProduction }
                result?.apply {
                    _tvShowList.postValue(this)
                }
            }
        }
    }

    private fun getEnded() {
        scope.launch(Dispatchers.IO) {
            watchedUseCase.watched()?.let {
                val result = watchedUseCase.watched()?.filter { !it.inProduction }
                result?.apply {
                    _tvShowList.postValue(this)
                }
            }
        }
    }

    private fun getRecommendations() {
        /*scope.launch(Dispatchers.IO) {
            recommendationsUseCase.invoke()?.let {
                val result = watchedUseCase.watched()?.filter { !it.inProduction }
                result?.apply {
                    _tvShowList.postValue(this)
                }
            }
        }*/
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