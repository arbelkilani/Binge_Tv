package com.arbelkilani.bingetv.data.model.image

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    @SerializedName("aspect_ratio") val aspectRatio: Double,
    @SerializedName("file_path") val _filePath: String,
    val height: Int,
    @SerializedName("iso_639_1") val iso6391: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    val width: Int
) : Parcelable {
    val filePath: String?
        get() = "https://image.tmdb.org/t/p/w780${this._filePath}"

}
