package com.arbelkilani.bingetv.data.entities.tv

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EpisodeToAir(
    @SerializedName("air_date")
    var airDate: String,

    @SerializedName("episode_number")
    val _episodeNumber: Int,

    val id: Int,
    val name: String,
    var overview: String,

    @SerializedName("production_code")
    val productionCode: String,

    @SerializedName("season_number")
    val _seasonNumber: Int,

    @SerializedName("show_id")
    val showId: Int,

    @SerializedName("still_path")
    val stillPath: String?,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Double,

    var airStamp: String,
    var airTime: String,
    var tv_next_episode: Int

) : Parcelable