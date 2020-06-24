package com.arbelkilani.bingetv.data.model.base

import com.arbelkilani.bingetv.data.model.tv.Tv

sealed class RepoResult {
    data class Success(val data: List<Tv>) : RepoResult()
    data class Error(val error: Exception) : RepoResult()
}
