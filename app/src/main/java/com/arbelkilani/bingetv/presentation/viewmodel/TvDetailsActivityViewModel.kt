package com.arbelkilani.bingetv.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.base.Status
import com.arbelkilani.bingetv.data.model.credit.Credit
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.data.model.tv.TvDetails
import com.arbelkilani.bingetv.data.model.tv.maze.details.NextEpisodeData
import com.arbelkilani.bingetv.data.model.video.VideoResponse
import com.arbelkilani.bingetv.domain.usecase.GetCreditsUseCase
import com.arbelkilani.bingetv.domain.usecase.GetNextEpisodeDataUseCase
import com.arbelkilani.bingetv.domain.usecase.GetTvDetailsUseCase
import kotlinx.coroutines.launch

class TvDetailsActivityViewModel constructor(
    private val selectedTvData: Tv,
    private val getTvDetailsUseCase: GetTvDetailsUseCase,
    private val getCreditsUseCase: GetCreditsUseCase,
    private val getNextEpisodeDataUseCase: GetNextEpisodeDataUseCase
) :
    BaseViewModel() {

    private val _selectedTv = MutableLiveData<Tv>(selectedTvData)
    val selectedTv: LiveData<Tv>
        get() = _selectedTv

    private val _tvId = MutableLiveData<Int>()
    val tvId: LiveData<Int>
        get() = _tvId

    private val TAG = TvDetailsActivityViewModel::class.java.simpleName

    private val _tvDetails = MutableLiveData<Resource<TvDetails>>()
    val tvDetails: LiveData<Resource<TvDetails>>
        get() = _tvDetails

    private val _credits = MutableLiveData<List<Credit>>()
    val credits: LiveData<List<Credit>>
        get() = _credits

    private val _nextEpisodeData = MutableLiveData<NextEpisodeData>()
    val nextEpisodeData: LiveData<NextEpisodeData>
        get() = _nextEpisodeData

    private val _trailerKey = MutableLiveData<String>()
    val trailerKey: LiveData<String>
        get() = _trailerKey

    private val _homePageUrl = MutableLiveData<String>()
    val homePageUrl: LiveData<String>
        get() = _homePageUrl

    init {
        scope.launch {
            _selectedTv.value?.let {
                getTvDetails(it)
                getCredits(it)
            }
        }
    }

    private suspend fun getCredits(selectedTv: Tv) {
        Log.i(TAG, "getCredits()")
        val response = getCreditsUseCase.invoke(selectedTv.id)
        if (response.status == Status.SUCCESS) {
            _credits.postValue(response.data!!.cast)
        }
    }

    private suspend fun getTvDetails(selectedTv: Tv) {
        Log.i(TAG, "getTvDetails()")
        _tvId.postValue(selectedTv.id)
        val response = getTvDetailsUseCase.invoke(selectedTv.id)
        if (response.status == Status.SUCCESS) {
            _tvDetails.postValue(Resource.success(response.data))
            if (response.data?.nextEpisodeToAir != null)
                getNextEpisodeDetails(response.data.id)
        } else {
            _tvDetails.postValue(Resource.exception(Exception(), null))
        }
    }

    private suspend fun getNextEpisodeDetails(id: Int) {
        Log.i(TAG, "getNextEpisodeDetails()")
        val response = getNextEpisodeDataUseCase.invoke(id)
        if (response.status == Status.SUCCESS)
            _nextEpisodeData.postValue(response.data)
    }

    fun playTrailer(videoResponse: VideoResponse) =
        _trailerKey.postValue(videoResponse.results[0].key)

    fun openHomePage(homePageUrl: String) {
        if (homePageUrl.isNotEmpty())
            _homePageUrl.postValue(homePageUrl)

    }

    fun saveToWatchlist() {
        scope.launch {
            getTvDetailsUseCase.saveToWatchlist(_tvDetails.value?.data!!)
        }
    }

    fun setTvShowWatched() {
        scope.launch {
            getTvDetailsUseCase.setTvShowWatched(_tvDetails.value?.data!!)
        }
    }

}