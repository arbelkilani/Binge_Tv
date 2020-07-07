package com.arbelkilani.bingetv.data.entities.video

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Video(
    val id: String,
    @SerializedName("iso_639_1") val iso6391: String,
    @SerializedName("iso_3166_1") val iso31661: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String
) : Parcelable
