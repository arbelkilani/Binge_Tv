package com.arbelkilani.bingetv.presentation.viewmodel

import android.util.Log
import com.arbelkilani.bingetv.domain.usecase.GetRequestMoreUseCase
import com.arbelkilani.bingetv.domain.usecase.TestUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DiscoverFragmentViewModel(
    private val requestMoreUseCase: GetRequestMoreUseCase,
    private val testUseCase: TestUseCase
) : BaseViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 15
        private const val TAG = "DiscoverViewModel"
    }

    init {
        scope.launch {
            testUseCase.invoke().catch { catch ->
                Log.e(TAG, "catch = $catch")
            }.collect { collect ->
                Log.i(TAG, "collect = $collect")
            }
        }
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            scope.launch {
                requestMoreUseCase.invoke()
            }
        }
    }
}