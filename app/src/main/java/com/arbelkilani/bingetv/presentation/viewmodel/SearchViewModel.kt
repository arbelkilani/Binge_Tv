package com.arbelkilani.bingetv.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.domain.usecase.GetSearchTvShowUseCase
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getSearchTvShowUseCase: GetSearchTvShowUseCase
) : BaseViewModel() {

    private val TAG = SearchViewModel::class.java.simpleName

    //TODO
    // Re understand live data and mutable live data for best performance
    val tvListLiveData = MutableLiveData<List<Tv>>()

    fun searchTvShow(query: String) {
        scope.launch {

            val response = getSearchTvShowUseCase.invoke(query)
            tvListLiveData.postValue(response.data?.results)
        }
    }

}