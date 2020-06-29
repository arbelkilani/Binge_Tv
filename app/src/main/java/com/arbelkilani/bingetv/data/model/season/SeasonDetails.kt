package com.arbelkilani.bingetv.data.model.season

import android.os.Parcelable
import com.arbelkilani.bingetv.data.model.episode.Episode
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SeasonDetails(
    @SerializedName("air_date") val airDate: String,
    val episodes: List<Episode>,
    val name: String,
    val overview: String,
    val id: Int,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("season_number") val seasonNumber: Int
) : Parcelable