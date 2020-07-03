package com.arbelkilani.bingetv.data.model.tv

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.arbelkilani.bingetv.data.model.genre.Genre
import com.arbelkilani.bingetv.data.model.image.ImageResponse
import com.arbelkilani.bingetv.data.model.season.Season
import com.arbelkilani.bingetv.data.model.video.VideoResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tv_table")
data class TvShow(
    @ColumnInfo(name = "backdrop_path") @SerializedName("backdrop_path") var backdropPath: String?,
    @Ignore @SerializedName("created_by") var createdBy: List<CreatedBy>,
    @ColumnInfo(name = "episode_run_time") @SerializedName("episode_run_time") var episodeRunTime: List<Int>,
    @ColumnInfo(name = "first_air_date") @SerializedName("first_air_date") var firstAirData: String,
    var genres: List<Genre>,
    var homepage: String,
    @ColumnInfo(name = "id") @PrimaryKey var id: Int,
    @ColumnInfo(name = "in_production") @SerializedName("in_production") var inProduction: Boolean,
    @Ignore var languages: List<String>,
    @ColumnInfo(name = "last_air_date") @SerializedName("last_air_date") var lastAirDate: String,
    @Ignore @ColumnInfo(name = "last_episode_to_air") @SerializedName("last_episode_to_air") var lastEpisodeToAir: EpisodeToAir?,
    var name: String,
    @ColumnInfo(name = "next_episode_to_air") @SerializedName("next_episode_to_air") var nextEpisodeToAir: EpisodeToAir?,
    var networks: List<Network>,
    @ColumnInfo(name = "number_of_episodes") @SerializedName("number_of_episodes") var numberOfEpisodes: Int,
    @ColumnInfo(name = "number_of_seasons") @SerializedName("number_of_seasons") var numberOfSeasons: Int,
    @Ignore @SerializedName("origin_country") var originCountry: List<String>,
    @ColumnInfo(name = "original_language") @SerializedName("original_language") var originalLanguage: String,
    @ColumnInfo(name = "original_name") @SerializedName("original_name") var originName: String,
    var overview: String,
    var popularity: Double,
    @ColumnInfo(name = "poster_path") @SerializedName("poster_path") var posterPath: String?,
    @Ignore @SerializedName("production_companies") var productionCompanies: List<ProductionCompany>,
    var seasons: List<Season>,
    var status: String,
    var type: String,
    @ColumnInfo(name = "vote_average") @SerializedName("vote_average") var voteAverage: Double,
    @ColumnInfo(name = "vote_count") @SerializedName("vote_count") var voteCount: Double,
    @Ignore var videos: VideoResponse?, //TODO perform action for type and size ( think about using enumerations instead)
    @Ignore var images: ImageResponse?,
    var watchlist: Boolean,
    var watched: Boolean
) : Parcelable {

    constructor() : this(
        "",
        listOf<CreatedBy>(),
        listOf<Int>(),
        "",
        listOf<Genre>(),
        "",
        0,
        false,
        listOf<String>(),
        "",
        null,
        "",
        null,
        listOf<Network>(),
        0,
        0,
        listOf<String>(),
        "",
        "",
        "",
        0.0,
        "",
        listOf<ProductionCompany>(),
        listOf<Season>(),
        "",
        "",
        0.0,
        0.0,
        null,
        null,
        false,
        false
    )


    val getPoster: String?
        get() = "https://image.tmdb.org/t/p/w500${this.posterPath}"

    val getBackdrop: String?
        get() = "https://image.tmdb.org/t/p/w500${this.backdropPath}"

    val getSeasonsNumber: String
        get() = String.format("%d %s", numberOfSeasons, "Seasons")

}