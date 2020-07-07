package com.arbelkilani.bingetv.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.bingetv.data.entities.base.Status
import com.arbelkilani.bingetv.data.entities.credit.Credit
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.domain.usecase.GetCreditsUseCase
import com.arbelkilani.bingetv.domain.usecase.GetTvDetailsUseCase
import com.arbelkilani.bingetv.domain.usecase.tv.UpdateTvShowUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TvDetailsActivityViewModel constructor(
    private val extraTvShowEntity: TvShowEntity,
    private val getTvDetailsUseCase: GetTvDetailsUseCase,
    private val getCreditsUseCase: GetCreditsUseCase,
    private val updateTvShowUseCase: UpdateTvShowUseCase
) :
    BaseViewModel() {

    private val _tvShowEntity = MutableLiveData<TvShowEntity>(extraTvShowEntity)
    val tvShowEntity: LiveData<TvShowEntity>
        get() = _tvShowEntity

    private val _credits = MutableLiveData<List<Credit>>()
    val credits: LiveData<List<Credit>>
        get() = _credits

    /*private val _tvId = MutableLiveData<Int>()
    val tvId: LiveData<Int>
        get() = _tvId






     */

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
        // _tvId.postValue(extraTvShowEntity.id)
        val response = getTvDetailsUseCase.invoke(extraTvShowEntity.id)
        if (response.status == Status.SUCCESS) {
            _tvShowEntity.postValue(response.data)
        }
    }

    private suspend fun getCredits(extraTvShowEntity: TvShowEntity) {
        val response = getCreditsUseCase.invoke(extraTvShowEntity.id)
        if (response.status == Status.SUCCESS) {
            _credits.postValue(response.data!!.cast)
        }
    }

    fun isTvShowWatched(watched: Boolean) {
        scope.launch(Dispatchers.IO) {
            val resulted = updateTvShowUseCase.saveWatched(watched, _tvShowEntity.value!!)
            _tvShowEntity.postValue(resulted)
        }
    }

    /*
    fun saveWatchlist(state: Boolean) {
        scope.launch {
            _tvDetails.value?.data!!.watchlist = state
            _tvDetails.postValue(Resource.success(_tvDetails.value?.data))
            getTvDetailsUseCase.saveWatchlist(_tvDetails.value?.data!!)
        }
    }

    fun saveWatched(state: Boolean) {
        scope.launch {
            _tvDetails.value?.data!!.watched = state
            _tvDetails.postValue(Resource.success(_tvDetails.value?.data))
            getTvDetailsUseCase.saveWatched(_tvDetails.value?.data!!)
        }
    }*/

}