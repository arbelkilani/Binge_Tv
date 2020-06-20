package com.arbelkilani.bingetv.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.base.Status
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.data.model.tv.TvDetails
import com.arbelkilani.bingetv.data.model.video.VideoResponse
import com.arbelkilani.bingetv.domain.usecase.GetTvDetailsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailsTvActivityViewModel constructor(private val getTvDetailsUseCase: GetTvDetailsUseCase) :
    BaseViewModel() {

    private val TAG = DetailsTvActivityViewModel::class.java.simpleName

    val resource = MutableLiveData<Resource<TvDetails>>()
    val trailerKey = MutableLiveData<String>()
    val homePageUrl = MutableLiveData<String>()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getDetails(selectedTv: Tv) {
        scope.launch {
            val response = getTvDetailsUseCase.invoke(selectedTv.id)
            //TODO recheck condition of error and success
            if (response.status == Status.SUCCESS) {
                resource.postValue(Resource.success(response.data))
            } else {
                resource.postValue(Resource.exception(Exception(), null))
            }
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