package com.arbelkilani.bingetv.data.entities.season

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.arbelkilani.bingetv.data.entities.episode.EpisodeData
import com.google.gson.annotations.SerializedName

/*@Entity(
    tableName = "season_table",
    foreignKeys = [ForeignKey(
        entity = TvShowData::class,
        parentColumns = ["id"],
        childColumns = ["tv_season"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["tv_season"], name = "index_tv_season")]
)*/
@Entity(tableName = "season_table")
data class SeasonData(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Int = -1,

    @Ignore
    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String = "",

    @Ignore
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    var overview: String = "",

    @Ignore
    @ColumnInfo(name = "air_date")
    @SerializedName("air_date")
    var airDate: String? = null,

    @ColumnInfo(name = "episode_count")
    @SerializedName("episode_count")
    var episodeCount: Int = 0,

    @Ignore
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var posterPath: String? = null,

    @ColumnInfo(name = "season_number")
    @SerializedName("season_number")
    var seasonNumber: Int = 0,

    @Ignore
    @SerializedName("episodes")
    var episodes: List<EpisodeData> = listOf(),

    @ColumnInfo(name = "tv_season")
    var tv_season: Int = -1,

    @ColumnInfo(name = "watched")
    var watched: Boolean = false,

    @ColumnInfo(name = "watched_count")
    var watchedCount: Int = 0,

    @ColumnInfo(name = "future_episode_count")
    var futureEpisodeCount: Int = 0

) {

    fun mapOf(): Map<String, String> {
        return mapOf(
            "id" to id.toString(),
            "episode_count" to episodeCount.toString(),
            "season_number" to seasonNumber.toString(),
            "tv_season" to tv_season.toString(),
            "watched" to if (watched) "1" else "0",
            "watched_count" to watchedCount.toString(),
            "future_episode_count" to futureEpisodeCount.toString()
        )
    }
}