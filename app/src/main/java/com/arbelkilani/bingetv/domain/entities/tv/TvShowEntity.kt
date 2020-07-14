package com.arbelkilani.bingetv.domain.entities.tv

import android.os.Parcelable
import com.arbelkilani.bingetv.data.entities.genre.Genre
import com.arbelkilani.bingetv.data.entities.tv.Network
import com.arbelkilani.bingetv.data.entities.tv.maze.details.NextEpisodeData
import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShowEntity(
    var id: Int = 0,
    var episodeCount: Int = 0,
    var name: String = "",
    var overview: String = "",
    var firstAirDate: String = "",
    var runtime: Int = 0,
    var status: String = "",
    var type: String = "",
    var voteAverage: Double = 0.0,
    var homepage: String = "",
    var nextEpisodeData: NextEpisodeData? = null,
    var genres: List<Genre> = listOf(),
    var networks: List<Network> = listOf(),
    var images: List<String>? = listOf(),
    var videos: List<String>? = listOf(),
    var seasonsCount: String = "",
    var seasons: List<SeasonEntity> = listOf(),
    var poster: String? = null,
    var backdrop: String? = null,
    var watched: Boolean = false,
    var watchlist: Boolean = false,
    var watchedCount: Int = 0
) : Parcelable