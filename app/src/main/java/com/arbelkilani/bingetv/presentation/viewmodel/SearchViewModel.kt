package com.arbelkilani.bingetv.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arbelkilani.bingetv.data.model.tv.TvShow
import com.arbelkilani.bingetv.domain.usecase.SearchUseCase
import kotlinx.coroutines.flow.Flow

class SearchViewModel(
    private val searchUseCase: SearchUseCase
) : BaseViewModel() {

    private val TAG = SearchViewModel::class.java.simpleName

    //TODO
    // Re understand live data and mutable live data for best performance
    val tvListLiveData = MutableLiveData<List<TvShow>>()

    suspend fun search(query: String): Flow<PagingData<TvShow>> {
        return searchUseCase.invoke(query).cachedIn(viewModelScope)
    }

}