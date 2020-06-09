package com.arbelkilani.bingetv.data.model.base

import android.util.Log
import com.arbelkilani.bingetv.data.enum.HttpStatusCode
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

data class Resource<out T>(val status: Status, val code: Int?, val message: String?, val data: T?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, HttpStatusCode.SUCCESS, null, data)
        }

        fun <T> error(errorBody: ResponseBody): Resource<T> {
            val errorResponse = Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java)
            return Resource(
                Status.ERROR,
                errorResponse.status_code,
                errorResponse.status_message,
                null
            )
        }

        fun <T> exception(exception: Exception): Resource<T> {
            return when (exception) {
                is HttpException, is UnknownHostException -> Resource(
                    Status.ERROR,
                    HttpStatusCode.NETWORK_ERROR,
                    exception.localizedMessage,
                    null
                )
                is SocketTimeoutException -> Resource(
                    Status.ERROR,
                    HttpStatusCode.SOCKET_TIMEOUT,
                    exception.localizedMessage,
                    null
                )
                else -> Resource(
                    Status.ERROR,
                    HttpStatusCode.UNKNOWN_ERROR,
                    exception.localizedMessage,
                    null
                )
            }
        }
    }
}
