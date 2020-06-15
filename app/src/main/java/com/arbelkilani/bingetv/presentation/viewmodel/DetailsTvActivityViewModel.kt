package com.arbelkilani.bingetv.presentation.viewmodel

import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.domain.usecase.GetTvDetailsUseCase
import kotlinx.coroutines.launch

class DetailsTvActivityViewModel constructor(private val getTvDetailsUseCase: GetTvDetailsUseCase) :
    BaseViewModel() {

    fun getDetails(selectedTv: Tv) {
        scope.launch {
            getTvDetailsUseCase.invoke(selectedTv.id)
        }
    }

}