package com.arbelkilani.bingetv.data.model.base

import okhttp3.ResponseBody

data class Resource<out T>(val code: Int?, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(null, data, null)
        }

        fun <T> success(code : Int, data: T): Resource<T> {
            return Resource(code, data, null)
        }

        fun <T> failure(message: String?, data: T?): Resource<T> {
            return Resource(null, data, message)
        }

        fun <T> failure(code: Int?, data: T?): Resource<T> {
            return Resource(code, data, null)
        }

        fun <T> failure(code: Int?, message: String?): Resource<T> {
            return Resource(code, null, message)
        }

        fun <T> failure(responseBody: ResponseBody?, data: T?): Resource<T> {
            //TODO parse response body
            return Resource(0, data, null)
        }
    }
}