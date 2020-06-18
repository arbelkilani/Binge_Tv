package com.arbelkilani.bingetv.data.model.tv

import android.os.Parcelable
import com.arbelkilani.bingetv.utils.returnDuration
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EpisodeToAir(
    @SerializedName("air_date") val _airDate: String,
    @SerializedName("episode_number") val episodeNumber: Int,
    val id: Int,
    val name: String,
    val overview: String,
    @SerializedName("production_code") val productionCode: String,
    @SerializedName("season_number") val seasonNumber: Int,
    @SerializedName("show_id") val showId: Int,
    @SerializedName("still_path") val stillPath: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Double
) : Parcelable {
    val airDate: String
        get() = _airDate // TODO format date

    val tillAirDateDuration: String
        get() = ", will be aired in ${returnDuration(_airDate)} days" //TODO workin on multilanguage value
}
