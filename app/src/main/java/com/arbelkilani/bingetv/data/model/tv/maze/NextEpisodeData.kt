package com.arbelkilani.bingetv.data.model.tv.maze

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NextEpisodeData(
    @SerializedName("airdate") val airDate: String?,
    @SerializedName("airstamp") val airStamp: String?,
    val summary: String
) : Parcelable