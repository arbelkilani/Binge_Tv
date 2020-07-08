package com.arbelkilani.bingetv.data.entities.episode

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
/*@Entity(
    tableName = "episode_table",
    foreignKeys = [ForeignKey(
        entity = TvShowData::class,
        parentColumns = ["id"],
        childColumns = ["season_episode"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["season_episode"], name = "index_season_episode")]
)*/
@Entity(tableName = "episode_table")
data class EpisodeData(

    @PrimaryKey
    @SerializedName("id")
    var id: Int = -1,

    @Ignore
    @ColumnInfo(name = "name")
    var name: String = "",

    @Ignore
    @ColumnInfo(name = "overview")
    var overview: String = "",

    @Ignore
    @ColumnInfo(name = "air_date")
    @SerializedName("air_date")
    var airDate: String = "",

    @Ignore
    @ColumnInfo(name = "episode_number")
    @SerializedName("episode_number")
    var episodeNumber: Int = 0,

    @Ignore
    @ColumnInfo(name = "still_path")
    @SerializedName("still_path")
    var stillPath: String? = "",

    @Ignore
    @SerializedName("vote_average")
    var voteAverage: Double = 0.0,


    @Ignore
    @ColumnInfo(name = "seasonNumber")
    @SerializedName("season_number")
    var seasonNumber: Int = 0,

    @Ignore
    @ColumnInfo(name = "production_code")
    @SerializedName("production_code")
    var production_code: String? = "",


    @Ignore
    @SerializedName("vote_count")
    var voteCount: Int = 0,

    @ColumnInfo(name = "season_episode")
    var season_episode: Int = -1,

    @ColumnInfo(name = "tv_episode")
    var tv_episode: Int = -1

) : Parcelable {

    /*val getEpisodeNumber: String
        get() = String.format("%d", episodeNumber, Locale.getDefault())

    val getAirDate: String
        get() = String.format("%s : %s", "Aired", airDate, Locale.getDefault())

    val getStillPath: String?
        get() = "https://image.tmdb.org/t/p/w300${this.stillPath}"

    val getVoteAverage: String?
        get() = spannableVoteRate(voteAverage.toString())*/
}