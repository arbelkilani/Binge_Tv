package com.arbelkilani.bingetv.data.entities.season

import android.os.Parcelable
import androidx.room.*
import com.arbelkilani.bingetv.data.entities.episode.Episode
import com.arbelkilani.bingetv.data.entities.tv.TvShowData
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "season_table",
    foreignKeys = [ForeignKey(
        entity = TvShowData::class,
        parentColumns = ["id"],
        childColumns = ["tv_season"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["tv_season"], name = "index_tv_season")]
)
data class SeasonData(

    @ColumnInfo(name = "air_date")
    @SerializedName("air_date")
    var airDate: String = "",

    @ColumnInfo(name = "episode_count")
    @SerializedName("episode_count")
    var episodeCount: Int = 0,

    @PrimaryKey
    @SerializedName("id")
    var id: Int = -1,

    @SerializedName("name")
    var name: String = "",

    @SerializedName("overview")
    var overview: String = "",

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var posterPath: String = "",

    @ColumnInfo(name = "season_number")
    @SerializedName("season_number")
    var seasonNumber: Int = 0,

    @SerializedName("episodes")
    var episodes: List<Episode> = listOf(),

    @ColumnInfo(name = "tv_season")
    var tv_season: Int = -1,

    @ColumnInfo(name = "watched")
    var watched: Boolean = false

) : Parcelable {

    /*val getPosterPath: String?
        get() = "https://image.tmdb.org/t/p/w500${this.posterPath}"

    val posterPath185: String?
        get() = "https://image.tmdb.org/t/p/w185${this.posterPath}"

    val getSeasonNumber: String
        get() = String.format("%s : %d", "Season", seasonNumber, Locale.getDefault())

    val getEpisodeCount: String
        get() {
            return if (watched)
                String.format("%d/%d", _episodeCount, _episodeCount) else String.format(
                "%d/%d",
                0,
                _episodeCount
            )
        }

    val getAirDate: String
        get() = String.format("First aired %s ", airDate, Locale.getDefault())

    val watchedCount: String
        get() = String.format("%d/%d", 0, _episodeCount, Locale.getDefault())*/


}