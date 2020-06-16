package com.arbelkilani.bingetv.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.base.Status
import com.arbelkilani.bingetv.data.model.tv.CombinedObjects
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.data.model.tv.TvDetails
import com.arbelkilani.bingetv.domain.usecase.GetTvDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailsTvActivityViewModel constructor(private val getTvDetailsUseCase: GetTvDetailsUseCase) :
    BaseViewModel() {

    private val TAG = DetailsTvActivityViewModel::class.java.simpleName

    val resource = MutableLiveData<Resource<TvDetails>>()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getDetails(selectedTv: Tv) {
        scope.launch {
            val response = getTvDetailsUseCase.invoke(selectedTv.id)
            Log.i(TAG, "response = ${response}")
            //Log.i(TAG, "response = ${response.data!!.genres}")

            if (response.status == Status.SUCCESS) {
                resource.postValue(Resource.success(response.data))
            } else {
                resource.postValue(Resource.exception(Exception(), null))
            }
        }
    }

}