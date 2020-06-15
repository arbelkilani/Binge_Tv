package com.arbelkilani.bingetv.data.model.base

import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.enum.HttpStatusCode
import com.arbelkilani.bingetv.data.model.tv.TvDetails
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.UnknownHostException

data class Resource<out T>(val status: Status, val code: Int?, val message: Int?, val data: T?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, HttpStatusCode.SUCCESS, null, data)
        }

        fun <T> error(errorBody: ResponseBody): Resource<T> {
            val errorResponse = Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java)
            return Resource(
                Status.ERROR,
                errorResponse.status_code,
                R.string.error_server,
                null
            )
        }

        //TODO Check warning
        fun <T> exception(exception: Throwable, message: Int?): Resource<T> {

            return when (exception) {
                is HttpException -> {
                    Resource(
                        Status.ERROR,
                        exception.code(),
                        R.string.error_server,
                        null
                    )
                }
                is UnknownHostException -> Resource(
                    Status.ERROR,
                    HttpStatusCode.NETWORK_ERROR,
                    R.string.error_network,
                    null
                )
                else -> Resource(
                    Status.ERROR,
                    HttpStatusCode.UNKNOWN_ERROR,
                    R.string.error_unknown,
                    null
                )
            }
        }
    }
}
