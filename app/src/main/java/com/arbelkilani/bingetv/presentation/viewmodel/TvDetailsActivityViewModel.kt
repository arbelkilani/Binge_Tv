package com.arbelkilani.bingetv.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.bingetv.data.entities.base.Status
import com.arbelkilani.bingetv.data.entities.credit.Credit
import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.domain.usecase.GetCreditsUseCase
import com.arbelkilani.bingetv.domain.usecase.GetTvDetailsUseCase
import com.arbelkilani.bingetv.domain.usecase.season.UpdateSeasonUseCase
import com.arbelkilani.bingetv.domain.usecase.tv.UpdateTvShowUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class TvDetailsActivityViewModel constructor(
    private val extraTvShowEntity: TvShowEntity,
    private val getTvDetailsUseCase: GetTvDetailsUseCase,
    private val getCreditsUseCase: GetCreditsUseCase,
    private val updateTvShowUseCase: UpdateTvShowUseCase,
    private val updateSeasonUseCase: UpdateSeasonUseCase
) :
    BaseViewModel() {

    private val _tvShowEntity = MutableLiveData<TvShowEntity>(extraTvShowEntity)
    val tvShowEntity: LiveData<TvShowEntity>
        get() = _tvShowEntity

    private val _credits = MutableLiveData<List<Credit>>()
    val credits: LiveData<List<Credit>>
        get() = _credits

    private val _seasons = MutableLiveData<List<SeasonEntity>>()
    val seasons: LiveData<List<SeasonEntity>>
        get() = _seasons

    companion object {
        const val TAG = "TvShowViewModel"
    }

    init {
        scope.launch(Dispatchers.IO) {
            _tvShowEntity.value?.let {
                getTvDetails(it)
                getCredits(it)
            }
        }
    }

    private suspend fun getTvDetails(extraTvShowEntity: TvShowEntity) {
        val response = getTvDetailsUseCase.invoke(extraTvShowEntity.id)

        if (response.status == Status.SUCCESS) {
            response.data?.let {
                _tvShowEntity.postValue(it)
                _seasons.postValue(it.seasons)
            }
        }
    }

    private suspend fun getCredits(extraTvShowEntity: TvShowEntity) {
        val response = getCreditsUseCase.invoke(extraTvShowEntity.id)
        if (response.status == Status.SUCCESS) {
            _credits.postValue(response.data!!.cast)
        }
    }

    fun saveWatched(watched: Boolean) {
        scope.launch(Dispatchers.IO) {
            val resulted = updateTvShowUseCase.saveWatched(watched, _tvShowEntity.value!!)
            resulted?.let {
                _tvShowEntity.postValue(it)
                _seasons.postValue(it.seasons)
            }
        }
    }

    fun saveWatchlist(watchlist: Boolean) {
        scope.launch(Dispatchers.IO) {
            val resulted = updateTvShowUseCase.saveWatchlist(watchlist, _tvShowEntity.value!!)
            _tvShowEntity.postValue(resulted)
        }
    }

    fun seasonWatchState(state: Boolean, seasonEntity: SeasonEntity): SeasonEntity {

        val job = runBlocking(Dispatchers.IO) {
            return@runBlocking withContext(Dispatchers.Default) {
                updateSeasonUseCase.saveWatched(
                    state,
                    seasonEntity,
                    _tvShowEntity.value!!
                )
            }
        }

        _tvShowEntity.postValue(_tvShowEntity.value)

        return job!!
    }

    fun refresh(tvShowEntity: TvShowEntity) {
        _tvShowEntity.postValue(tvShowEntity)
    }
}