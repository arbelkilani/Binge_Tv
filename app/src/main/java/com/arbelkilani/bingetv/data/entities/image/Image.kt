package com.arbelkilani.bingetv.data.entities.image

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    @SerializedName("aspect_ratio")
    val aspectRatio: Double,

    @SerializedName("file_path")
    var filePath: String,

    @SerializedName("height")
    val height: Int,

    @SerializedName("iso_639_1")
    val iso6391: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("width")
    val width: Int
) : Parcelable