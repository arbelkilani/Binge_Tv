package com.arbelkilani.bingetv.data.model.base

import okhttp3.ResponseBody

open class ResponseHandler {

    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleSuccess(code : Int, data: T): Resource<T> {
        return Resource.success(code, data)
    }

    fun <T : Any> handleFailure(message: String): Resource<T> {
        return Resource.failure(message, null)
    }

    fun <T : Any> handleFailure(code: Int): Resource<T> {
        return Resource.failure(code, null)
    }

    fun <T : Any> handleFailure(code: Int, message: String): Resource<T> {
        return Resource.failure(code, message)
    }

    fun <T : Any> handleFailure(responseBody: ResponseBody): Resource<T> {
        return Resource.failure(responseBody, null)
    }

}