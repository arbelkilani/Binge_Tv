package com.arbelkilani.bingetv.domain.entities.tv

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NextEpisodeEntity(
    var name: String = "",
    var season: Int = 0,
    var number: Int = 0,
    var summary: String? = "",
    var time: Long = 0L
) : Parcelable