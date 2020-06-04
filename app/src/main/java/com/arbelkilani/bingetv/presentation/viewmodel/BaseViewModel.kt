package com.arbelkilani.bingetv.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    val scope = CoroutineScope(
        Job() + Dispatchers.Main
    )

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

}