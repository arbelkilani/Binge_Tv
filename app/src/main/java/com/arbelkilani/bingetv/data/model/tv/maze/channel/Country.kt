package com.arbelkilani.bingetv.data.model.tv.maze.channel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(
    val name: String,
    val code: String,
    @SerializedName("timezone") val _timezone: String
) : Parcelable {

    val timezone: String
        get() = if (_timezone.isEmpty())
            "America/New_York"
        else
            _timezone
}