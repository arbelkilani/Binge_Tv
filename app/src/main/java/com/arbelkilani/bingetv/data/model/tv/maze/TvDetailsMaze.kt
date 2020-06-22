package com.arbelkilani.bingetv.data.model.tv.maze

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvDetailsMaze(
    val id: Int,
    @SerializedName("_links") val links: Links?
) : Parcelable