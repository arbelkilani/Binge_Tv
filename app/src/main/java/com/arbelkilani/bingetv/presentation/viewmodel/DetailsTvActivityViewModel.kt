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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class DetailsTvActivityViewModel constructor(
    private val getTvDetailsUseCase: GetTvDetailsUseCase,
    private val getCreditsUseCase: GetCreditsUseCase,
    private val getNextEpisodeDataUseCase: GetNextEpisodeDataUseCase
) :
    BaseViewModel() {

    private val _tvId = MutableLiveData<Int>()
    val tvId: LiveData<Int>
        get() = _tvId

    private val TAG = DetailsTvActivityViewModel::class.java.simpleName

    val tvDetailsLiveData = MutableLiveData<Resource<TvDetails>>()
    val creditsLiveData = MutableLiveData<List<Credit>>()
    val nextEpisodeData = MutableLiveData<NextEpisodeData>()

    val trailerKey = MutableLiveData<String>()
    val homePageUrl = MutableLiveData<String>()

    fun playTrailer(videoResponse: VideoResponse) =
        trailerKey.postValue(videoResponse.results[0].key)

    fun openHomePage(homePageUrl: String) {
        if (homePageUrl.isNotEmpty())
            this.homePageUrl.postValue(homePageUrl)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getDetails(selectedTv: Tv) {
        scope.launch(Dispatchers.IO) {
            getTvDetails(selectedTv)
            getCredits(selectedTv)
        }
    }

    private suspend fun getCredits(selectedTv: Tv) {
        Log.i(TAG, "getCredits()")
        val response = getCreditsUseCase.invoke(selectedTv.id)
        if (response.status == Status.SUCCESS) {
            Log.i(TAG, "credits response = ${response.data}")
            creditsLiveData.postValue(response.data!!.cast)
        }
    }

    private suspend fun getTvDetails(selectedTv: Tv) {
        Log.i(TAG, "getTvDetails()")
        _tvId.postValue(selectedTv.id)
        val response = getTvDetailsUseCase.invoke(selectedTv.id)
        if (response.status == Status.SUCCESS) {
            tvDetailsLiveData.postValue(Resource.success(response.data))
            if (response.data?.nextEpisodeToAir != null)
                getNextEpisodeDetails(response.data.id)
        } else {
            tvDetailsLiveData.postValue(Resource.exception(Exception(), null))
        }
    }

    private suspend fun getNextEpisodeDetails(id: Int) {
        Log.i(TAG, "getNextEpisodeDetails()")
        val response = getNextEpisodeDataUseCase.invoke(id)
        if (response.status == Status.SUCCESS)
            nextEpisodeData.postValue(response.data)
    }

}