package com.arbelkilani.bingetv.domain.entities.season

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SeasonEntity(
    var id: Int = 0,
    var episodeCount: Int = 0,
    var name: String = "",
    var overview: String = "",
    var poster: String = "",
    var watched: Boolean = false,
    var watchedEpisodeCount: Int = 0,
    var progress: Int = (watchedEpisodeCount / episodeCount) * 100
) : Parcelable