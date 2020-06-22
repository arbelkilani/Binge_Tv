package com.arbelkilani.bingetv.data.model.tv.maze.details

import android.os.Parcelable
import androidx.core.text.HtmlCompat
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NextEpisodeData(
    val name: String,
    val season: Int,
    val number: Int,
    @SerializedName("airdate") val airDate: String,
    @SerializedName("airstamp") val airStamp: String,
    @SerializedName("airtime") val _airTime: String,
    @SerializedName("summary") val _summary: String,
    var timezone: String,
    var formattedAirDate: String
) : Parcelable {
    val airTime: String
        get() {
            return if (_airTime.isEmpty())
                "00:00" else _airTime
        }

    fun formattedSeasonEpisode(): String {
        return String.format("%02dx%02d", season, number)
    }

    val summary: String
        get() = HtmlCompat.fromHtml(_summary, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
}