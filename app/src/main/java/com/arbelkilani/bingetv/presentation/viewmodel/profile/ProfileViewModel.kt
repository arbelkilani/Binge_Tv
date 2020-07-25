package com.arbelkilani.bingetv.presentation.viewmodel.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.bingetv.domain.entities.genre.GenreEntity
import com.arbelkilani.bingetv.domain.entities.profile.StatisticsEntity
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

    private val _statistics = MutableLiveData<StatisticsEntity>()
    val statistics: LiveData<StatisticsEntity>
        get() = _statistics

    private val _genres = MutableLiveData<List<GenreEntity>>()
    val genres: LiveData<List<GenreEntity>>
        get() = _genres

    fun refresh() {
        getStatistics()
    }

    private fun getStatistics() {
        scope.launch(Dispatchers.IO) {
            _statistics.postValue(statisticsUseCase.getStatistics())
            _genres.postValue(statisticsUseCase.getGenres())
        }
    }
}