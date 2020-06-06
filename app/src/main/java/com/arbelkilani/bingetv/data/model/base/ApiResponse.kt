package com.arbelkilani.bingetv.data.model.base

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ApiResponse<T : Parcelable>(
    val page: Int,
    val results: List<T>,
    val total_pages: Int,
    val total_results: Int
) : Parcelable