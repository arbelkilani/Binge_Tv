package com.arbelkilani.bingetv.presentation.viewmodel.profile

import android.util.Log
import com.arbelkilani.bingetv.domain.usecase.profile.StatisticsUseCase
import com.arbelkilani.bingetv.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val statisticsUseCase: StatisticsUseCase
) : BaseViewModel() {

    companion object {
        private const val TAG = "ProfileViewModel"
    }

    init {

        scope.launch(Dispatchers.IO) {
            val size = statisticsUseCase.getWatchedEpisodesCount()
            Log.i("TAG++", "size = $size")
        }
    }
}