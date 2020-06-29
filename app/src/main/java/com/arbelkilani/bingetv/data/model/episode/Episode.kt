package com.arbelkilani.bingetv.data.model.episode

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Episode(
    @SerializedName("air_date") val _airDate: String,
    @SerializedName("episode_number") val _episodeNumber: Int,
    val name: String,
    val overview: String,
    val id: Int,
    @SerializedName("production_code") val production_code: String?,
    @SerializedName("season_number") val seasonNumber: Int,
    @SerializedName("still_path") val stillPath: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
) : Parcelable {

    val episodeNumber: String
        get() = String.format("%d", _episodeNumber, Locale.getDefault())

    val airDate: String
        get() = String.format("%s : %s", "Aired", _airDate, Locale.getDefault())
}