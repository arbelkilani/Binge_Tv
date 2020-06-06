package com.arbelkilani.bingetv.data.model.base

import okhttp3.ResponseBody

data class Resource<out T>(val status: Status?, val data: T?, val code: Int?) {

    companion object {

        fun <T> success(data: T, code: Int): Resource<T> = Resource(Status.SUCCESS, data, code)

        fun <T> error(data: T?, code: Int): Resource<T> = Resource(Status.ERROR, data, code)

    }

}