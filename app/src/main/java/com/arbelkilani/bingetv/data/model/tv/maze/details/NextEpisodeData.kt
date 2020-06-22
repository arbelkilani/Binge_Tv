package com.arbelkilani.bingetv.data.model.tv.maze.details

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NextEpisodeData(
    @SerializedName("airdate") val airDate: String,
    @SerializedName("airstamp") val airStamp: String,
    @SerializedName("airtime") val _airTime: String,
    val summary: String,
    var timezone: String,
    var formattedAirDate: String
) : Parcelable {
    val airTime: String
        get() {
            return if (_airTime.isEmpty())
                "00:00" else _airTime
        }
}