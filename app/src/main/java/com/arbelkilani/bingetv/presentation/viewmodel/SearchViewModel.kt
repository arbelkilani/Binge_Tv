package com.arbelkilani.bingetv.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.domain.usecase.SearchUseCase
import kotlinx.coroutines.flow.Flow

class SearchViewModel(
    private val searchUseCase: SearchUseCase
) : BaseViewModel() {

    companion object {
        private const val TAG = "SearchViewModel"
    }

    suspend fun search(query: String): Flow<PagingData<TvShowEntity>> {
        return searchUseCase.invoke(query).cachedIn(viewModelScope)
    }

}