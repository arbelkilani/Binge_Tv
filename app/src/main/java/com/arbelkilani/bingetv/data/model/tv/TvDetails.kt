package com.arbelkilani.bingetv.data.model.tv

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvDetails(
    val backdrop_path : String?,
    val created_by : List<CreatedBy>
) : Parcelable