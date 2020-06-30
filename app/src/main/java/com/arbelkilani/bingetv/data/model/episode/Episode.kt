package com.arbelkilani.bingetv.data.model.episode

import android.os.Parcelable
import com.arbelkilani.bingetv.utils.spannableVoteRate
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
    @SerializedName("still_path") val _stillPath: String?,
    @SerializedName("vote_average") val _voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
) : Parcelable {

    val episodeNumber: String
        get() = String.format("%d", _episodeNumber, Locale.getDefault())

    val airDate: String
        get() = String.format("%s : %s", "Aired", _airDate, Locale.getDefault())

    val stillPath: String?
        get() = "https://image.tmdb.org/t/p/w300${this._stillPath}"

    val voteAverage: String?
        get() = spannableVoteRate(_voteAverage.toString())
}