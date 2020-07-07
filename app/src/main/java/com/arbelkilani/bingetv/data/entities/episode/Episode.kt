package com.arbelkilani.bingetv.data.entities.episode

import android.os.Parcelable
import androidx.room.*
import com.arbelkilani.bingetv.data.entities.tv.TvShowData
import com.arbelkilani.bingetv.utils.spannableVoteRate
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(
    tableName = "episode_table",
    foreignKeys = [ForeignKey(
        entity = TvShowData::class,
        parentColumns = ["id"],
        childColumns = ["season_episode"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["season_episode"], name = "index_season_episode")]
)
data class Episode(
    @SerializedName("air_date") val airDate: String,
    @SerializedName("episode_number") val episodeNumber: Int,
    val name: String,
    val overview: String,
    @PrimaryKey val id: Int,
    @SerializedName("production_code") val production_code: String?,
    @SerializedName("season_number") val seasonNumber: Int,
    @SerializedName("still_path") val stillPath: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @ColumnInfo(name = "season_episode") var season_episode: Int
) : Parcelable {

    val getEpisodeNumber: String
        get() = String.format("%d", episodeNumber, Locale.getDefault())

    val getAirDate: String
        get() = String.format("%s : %s", "Aired", airDate, Locale.getDefault())

    val getStillPath: String?
        get() = "https://image.tmdb.org/t/p/w300${this.stillPath}"

    val getVoteAverage: String?
        get() = spannableVoteRate(voteAverage.toString())
}