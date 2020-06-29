package com.arbelkilani.bingetv.presentation.viewmodel.season

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.bingetv.data.model.base.Status
import com.arbelkilani.bingetv.data.model.episode.Episode
import com.arbelkilani.bingetv.data.model.season.Season
import com.arbelkilani.bingetv.domain.usecase.SeasonDetailsUseCase
import com.arbelkilani.bingetv.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class SeasonDetailsViewModel(
    tvId: Int,
    seasonData: Season,
    seasonDetailsUseCase: SeasonDetailsUseCase
) : BaseViewModel() {

    private val _season = MutableLiveData<Season>(seasonData)
    val season: LiveData<Season>
        get() = _season

    private val _episodes = MutableLiveData<List<Episode>>()
    val episodes: LiveData<List<Episode>>
        get() = _episodes

    companion object {
        private const val TAG = "SeasonDetails"
    }

    init {
        Log.i(TAG, "init")
        scope.launch {
            val response = seasonDetailsUseCase.invoke(tvId, _season.value!!._seasonNumber)
            when (response.status) {
                Status.SUCCESS -> {
                    response.data?.let {
                        _episodes.postValue(it.episodes.asReversed())
                    }

                }
                Status.ERROR -> Log.i(TAG, "error")
            }
        }
    }
}