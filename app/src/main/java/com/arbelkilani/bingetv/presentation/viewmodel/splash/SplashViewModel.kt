package com.arbelkilani.bingetv.presentation.viewmodel.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.bingetv.domain.usecase.SaveGenreUseCase
import com.arbelkilani.bingetv.domain.usecase.profile.ProfileUseCase
import com.arbelkilani.bingetv.domain.usecase.tv.UpdateNextEpisodeUseCase
import com.arbelkilani.bingetv.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SplashViewModel constructor(
    private val profileUseCase: ProfileUseCase,
    private val saveGenreUseCase: SaveGenreUseCase,
    private val updateNextEpisodeUseCase: UpdateNextEpisodeUseCase
) : BaseViewModel() {

    companion object {
        const val TAG = "SplashViewModel"
    }

    private val _state = MutableLiveData<Boolean>(false)
    val state: LiveData<Boolean>
        get() = _state

    init {
        fetchData()
        val isConnected = profileUseCase.isConnected()
    }

    private fun fetchData() {
        scope.launch(Dispatchers.IO) {
            val nextEpisode = async {
                updateNextEpisodeUseCase.updateNextEpisode()
            }
            val genres = async {
                saveGenreUseCase.invoke().let { _state.postValue(true) }
            }

            nextEpisode.await()
            genres.await()
        }
    }
}