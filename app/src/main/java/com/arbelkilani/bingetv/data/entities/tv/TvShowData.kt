package com.arbelkilani.bingetv.data.entities.tv

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.arbelkilani.bingetv.data.entities.genre.Genre
import com.arbelkilani.bingetv.data.entities.image.ImageResponse
import com.arbelkilani.bingetv.data.entities.season.Season
import com.arbelkilani.bingetv.data.entities.tv.maze.details.NextEpisodeData
import com.arbelkilani.bingetv.data.entities.video.VideoResponse
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tv_table")
data class TvShowData(

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,

    @Ignore
    @SerializedName("created_by")
    var createdBy: List<CreatedBy> = listOf(),

    @ColumnInfo(name = "episode_run_time")
    @SerializedName("episode_run_time")
    var episodeRunTime: List<Int> = listOf(),

    @ColumnInfo(name = "first_air_date")
    @SerializedName("first_air_date")
    var firstAirData: String = "",

    @Ignore
    @SerializedName("genres")
    var genres: List<Genre> = listOf(),

    @ColumnInfo(name = "homepage")
    @SerializedName("homepage")
    var homepage: String = "",

    @ColumnInfo(name = "id")
    @PrimaryKey
    var id: Int = -1,

    @ColumnInfo(name = "in_production")
    @SerializedName("in_production")
    var inProduction: Boolean = false,

    @Ignore
    var languages: List<String> = listOf(),

    @ColumnInfo(name = "last_air_date")
    @SerializedName("last_air_date")
    var lastAirDate: String = "",

    @Ignore
    @ColumnInfo(name = "last_episode_to_air")
    @SerializedName("last_episode_to_air")
    var lastEpisodeToAir: EpisodeToAir? = null,

    @ColumnInfo(name = "name")
    var name: String = "",

    @Ignore
    @ColumnInfo(name = "next_episode_to_air")
    @SerializedName("next_episode_to_air")
    var nextEpisodeToAir: EpisodeToAir? = null,

    @Ignore
    @SerializedName("networks")
    var networks: List<Network> = listOf(),

    @ColumnInfo(name = "number_of_episodes")
    @SerializedName("number_of_episodes")
    var numberOfEpisodes: Int = 0,

    @ColumnInfo(name = "number_of_seasons")
    @SerializedName("number_of_seasons")
    var numberOfSeasons: Int = 0,

    @Ignore
    @SerializedName("origin_country")
    var originCountry: List<String> = listOf(),

    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    var originalLanguage: String = "",

    @ColumnInfo(name = "original_name")
    @SerializedName("original_name")
    var originName: String = "",

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    var overview: String = "",

    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    var popularity: Double = 0.0,

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var posterPath: String? = null,

    @Ignore
    @SerializedName("production_companies")
    var productionCompanies: List<ProductionCompany> = listOf(),

    @Ignore
    @SerializedName("seasons")
    var seasons: List<Season> = listOf(),

    @SerializedName("status")
    var status: String = "",

    @SerializedName("type")
    var type: String = "",

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    var voteAverage: Double = 0.0,

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    var voteCount: Double = 0.0,

    @Ignore
    @SerializedName("videos")
    var videos: VideoResponse? = null,

    @Ignore
    @SerializedName("images")
    var images: ImageResponse? = null,

    @ColumnInfo(name = "watchlist")
    var watchlist: Boolean = false,

    @ColumnInfo(name = "watched")
    var watched: Boolean = false,

    @Ignore
    var nextEpisode: NextEpisodeData? = null

)