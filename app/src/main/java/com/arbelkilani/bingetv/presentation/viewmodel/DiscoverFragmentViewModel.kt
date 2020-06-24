package com.arbelkilani.bingetv.presentation.viewmodel

import android.util.Log
import com.arbelkilani.bingetv.domain.usecase.GetRequestMoreUseCase
import kotlinx.coroutines.launch

class DiscoverFragmentViewModel(
    private val requestMoreUseCase: GetRequestMoreUseCase
) : BaseViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
        private const val TAG = "DiscoverViewModel"
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            scope.launch {
                val response = requestMoreUseCase.invoke()
                Log.i(TAG, "Response = $response")
            }
        }
    }
}