package com.arbelkilani.bingetv.data.entities.tv.maze.details

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NextEpisodeData(

    @SerializedName("name")
    var name: String = "",

    @SerializedName("season")
    var season: Int = 0,

    @SerializedName("number")
    var number: Int = 0,

    @SerializedName("airdate")
    var airDate: String? = "",

    @SerializedName("airstamp")
    var airStamp: String = "",

    @SerializedName("airtime")
    var airTime: String = "",

    @SerializedName("summary")
    var summary: String? = null,

    var time: Long = 0L

) : Parcelable