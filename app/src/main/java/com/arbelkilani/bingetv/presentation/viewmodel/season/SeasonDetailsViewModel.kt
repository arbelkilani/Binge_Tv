package com.arbelkilani.bingetv.presentation.viewmodel.season

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.bingetv.domain.entities.episode.EpisodeEntity
import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.domain.usecase.episode.UpdateEpisodeUseCase
import com.arbelkilani.bingetv.domain.usecase.season.GetSeasonDetailsUseCase
import com.arbelkilani.bingetv.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SeasonDetailsViewModel(
    extraSeasonEntity: SeasonEntity,
    extraTvShowEntity: TvShowEntity,
    getSeasonDetailsUseCase: GetSeasonDetailsUseCase,
    private val updateEpisodeUseCase: UpdateEpisodeUseCase
) : BaseViewModel() {


    private val _season = MutableLiveData<SeasonEntity>(extraSeasonEntity)
    val season: LiveData<SeasonEntity>
        get() = _season

    private val _tvShow = MutableLiveData<TvShowEntity>(extraTvShowEntity)
    val tvShow: LiveData<TvShowEntity>
        get() = _tvShow

    private val _episodes = MutableLiveData<List<EpisodeEntity>>()
    val episodes: LiveData<List<EpisodeEntity>>
        get() = _episodes

    companion object {
        private const val TAG = "SeasonDetailsViewModel"
    }

    init {

        scope.launch(Dispatchers.IO) {
            val response = getSeasonDetailsUseCase.invoke(extraTvShowEntity, extraSeasonEntity)
            response.data?.let {
                _season.postValue(it)
                _episodes.postValue(it.episodes)
            }
        }
    }

    fun episodeWatchState(state: Boolean, episodeEntity: EpisodeEntity): EpisodeEntity {

        val job = runBlocking(Dispatchers.IO) {
            return@runBlocking withContext(Dispatchers.Default) {
                updateEpisodeUseCase.saveWatched(
                    state,
                    episodeEntity,
                    _tvShow.value?.id!!,
                    _season.value?.id!!
                )

            }
        }

        return job!!
    }
}