package com.arbelkilani.bingetv.data.entities.tv.maze.channel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WebChannel(
    val id: Int,
    val name: String,
    val country: Country?
) : Parcelable