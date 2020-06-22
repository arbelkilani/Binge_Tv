package com.arbelkilani.bingetv.data.model.tv.maze

import android.os.Parcelable
import com.arbelkilani.bingetv.data.model.tv.maze.channel.WebChannel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvDetailsMaze(
    val id: Int,
    val webChannel: WebChannel?,
    val network: WebChannel?,
    @SerializedName("_links") val links: Links?
) : Parcelable