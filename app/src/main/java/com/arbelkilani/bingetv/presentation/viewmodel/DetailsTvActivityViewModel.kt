package com.arbelkilani.bingetv.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.base.Status
import com.arbelkilani.bingetv.data.model.credit.Cast
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.data.model.tv.TvDetails
import com.arbelkilani.bingetv.data.model.video.VideoResponse
import com.arbelkilani.bingetv.domain.usecase.GetCreditsUseCase
import com.arbelkilani.bingetv.domain.usecase.GetTvDetailsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailsTvActivityViewModel constructor(
    private val getTvDetailsUseCase: GetTvDetailsUseCase,
    private val getCreditsUseCase: GetCreditsUseCase
) :
    BaseViewModel() {

    private val TAG = DetailsTvActivityViewModel::class.java.simpleName

    val tvDetailsLiveData = MutableLiveData<Resource<TvDetails>>()
    val creditsLiveData = MutableLiveData<List<Cast>>()

    val trailerKey = MutableLiveData<String>()
    val homePageUrl = MutableLiveData<String>()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getDetails(selectedTv: Tv) {
        scope.launch {
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
        } else {
            //TODO handle error by creating other error live data
            // this live data should be used all among this view model
        }
    }

    private suspend fun getTvDetails(selectedTv: Tv) {
        Log.i(TAG, "getTvDetails()")
        val response = getTvDetailsUseCase.invoke(selectedTv.id)
        //TODO recheck condition of error and success
        if (response.status == Status.SUCCESS) {
            tvDetailsLiveData.postValue(Resource.success(response.data))
        } else {
            tvDetailsLiveData.postValue(Resource.exception(Exception(), null))
        }
    }

    fun playTrailer(videoResponse: VideoResponse) {
        trailerKey.postValue(videoResponse.results[0].key)
    }

    fun openHomePage(homePageUrl: String) {
        if (homePageUrl.isNotEmpty())
            this.homePageUrl.postValue(homePageUrl)

    }

}