package com.arbelkilani.bingetv.data.entities.tv.maze.details

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NextEpisodeData(

    @SerializedName("name")
    val name: String,

    @SerializedName("season")
    val season: Int,

    @SerializedName("number")
    val number: Int,

    @SerializedName("airdate")
    val airDate: String,

    @SerializedName("airstamp")
    val airStamp: String,

    @SerializedName("airtime")
    var airTime: String,

    @SerializedName("summary")
    val summary: String?,

    var timezone: String,

    var formattedAirDate: String

) : Parcelable