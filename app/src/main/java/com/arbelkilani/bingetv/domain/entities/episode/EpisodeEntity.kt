package com.arbelkilani.bingetv.domain.entities.episode

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EpisodeEntity(
    var id: Int = 0,
    var name: String = "",
    var overview: String = "",
    var airDate: String? = null,
    var episodeNumber: Int = 0,
    var stillPath: String = "",
    var voteAverage: Double = 0.0,
    var watched: Boolean = false
) : Parcelable