package com.arbelkilani.bingetv.presentation.viewmodel.season

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.bingetv.data.entities.base.Status
import com.arbelkilani.bingetv.data.entities.episode.Episode
import com.arbelkilani.bingetv.data.entities.season.SeasonData
import com.arbelkilani.bingetv.data.entities.tv.TvShowData
import com.arbelkilani.bingetv.domain.usecase.SeasonDetailsUseCase
import com.arbelkilani.bingetv.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class SeasonDetailsViewModel(
    selectedTvData: TvShowData,
    seasonDataData: SeasonData,
    seasonDetailsUseCase: SeasonDetailsUseCase
) : BaseViewModel() {

    private val _season = MutableLiveData<SeasonData>(seasonDataData)
    val season: LiveData<SeasonData>
        get() = _season

    private val _episodes = MutableLiveData<List<Episode>>()
    val episodes: LiveData<List<Episode>>
        get() = _episodes

    private val _selectedTv = MutableLiveData<TvShowData>(selectedTvData)
    val selectedTv: LiveData<TvShowData>
        get() = _selectedTv

    companion object {
        private const val TAG = "SeasonDetails"
    }

    init {
        Log.i(TAG, "init")
        scope.launch {
            val response =
                seasonDetailsUseCase.invoke(_selectedTv.value!!.id, _season.value!!.seasonNumber)
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