package com.arbelkilani.bingetv.data.model.tv

import android.os.Parcelable
import com.arbelkilani.bingetv.data.model.genre.Genre
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvDetails(
    @SerializedName("backdrop_path") val _backdropPath: String?,
    @SerializedName("created_by") val createdBy: List<CreatedBy>,
    @SerializedName("episode_run_time") val episodeRunTime: List<Int>,
    @SerializedName("first_air_date") val firstAirData: String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @SerializedName("in_production") val inProduction: Boolean,
    val languages: List<String>,
    @SerializedName("last_air_date") val lastAirDate: String,
    @SerializedName("last_episode_to_air") val lastEpisodeToAir: EpisodeToAir,
    val name: String,
    @SerializedName("next_episode_to_air") val nextEpisodeToAir: EpisodeToAir?,
    val networks: List<Network>,
    @SerializedName("number_of_episodes") val numberOfEpisodes: Int,
    @SerializedName("number_of_seasons") val numberOfSeasons: Int,
    @SerializedName("origin_country") val originCountry: List<String>,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_name") val originName: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path") val _posterPath: String?,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompany>,
    val seasons: List<Season>,
    val status: String,
    val type: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Double,
    val videos: VideoResponse, //TODO perform action for type and size ( think about using enumerations instead)
    val images: ImageResponse
) : Parcelable {

    val posterPath: String?
        get() = "https://image.tmdb.org/t/p/w500${this._posterPath}"

    val backdropPath: String?
        get() = "https://image.tmdb.org/t/p/w500${this._backdropPath}"

}