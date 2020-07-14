package com.arbelkilani.bingetv.domain.entities.season

import android.os.Parcelable
import com.arbelkilani.bingetv.domain.entities.episode.EpisodeEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SeasonEntity(
    var id: Int = 0,
    var seasonNumber: Int = 0,
    var episodeCount: Int = 0,
    var name: String = "",
    var overview: String = "",
    var airDate: String = "",
    var poster: String = "",
    var watched: Boolean = false,
    var watchedCount: Int = 0,
    var episodes: List<EpisodeEntity> = listOf(),
    var futureEpisodeCount: Int = 0
) : Parcelable