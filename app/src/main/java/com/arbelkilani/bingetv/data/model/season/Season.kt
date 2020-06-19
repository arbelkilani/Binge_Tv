package com.arbelkilani.bingetv.data.model.season

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Season(
    @SerializedName("air_date") val airDate: String,
    @SerializedName("episode_count") val episodeCount: Int,
    val id: Int,
    val name: String,
    val overview: String,
    @SerializedName("poster_path") val _posterPath: String,
    @SerializedName("season_number") val seasonNumber: Int
) : Parcelable {
    val posterPath: String?
        get() = "https://image.tmdb.org/t/p/w500${this._posterPath}"
}
