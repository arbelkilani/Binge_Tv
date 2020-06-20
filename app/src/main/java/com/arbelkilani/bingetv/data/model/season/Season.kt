package com.arbelkilani.bingetv.data.model.season

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Season(
    @SerializedName("air_date") val _airDate: String,
    @SerializedName("episode_count") val _episodeCount: Int,
    val id: Int,
    val name: String,
    val overview: String,
    @SerializedName("poster_path") val _posterPath: String,
    @SerializedName("season_number") val _seasonNumber: Int
) : Parcelable {
    val posterPath: String?
        get() = "https://image.tmdb.org/t/p/w500${this._posterPath}"

    val posterPath185: String?
        get() = "https://image.tmdb.org/t/p/w185${this._posterPath}"

    val seasonNumber: String
        get() = String.format("%s : %d", "Season", _seasonNumber, Locale.getDefault())

    val episodeCount: String
        get() = String.format("%d %s ", _episodeCount, "Episodes", Locale.getDefault())

    val airDate: String
        get() = String.format("First aired %s ", _airDate, Locale.getDefault())
}
