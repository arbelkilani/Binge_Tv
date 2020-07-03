package com.arbelkilani.bingetv.data.model.tv

import android.os.Parcelable
import androidx.room.*
import com.arbelkilani.bingetv.utils.returnDuration
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "episode_to_air_table",
    foreignKeys = [ForeignKey(
        entity = TvShow::class,
        parentColumns = ["id"],
        childColumns = ["tv_next_episode"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["tv_next_episode"], name = "index_tv_next_episode")]
)
data class EpisodeToAir(
    @ColumnInfo(name = "air_date") @SerializedName("air_date") var airDate: String,
    @ColumnInfo(name = "episode_number") @SerializedName("episode_number") val _episodeNumber: Int,
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "overview") var overview: String,
    @SerializedName("production_code") val productionCode: String,
    @ColumnInfo(name = "seasonNumber") @SerializedName("season_number") val _seasonNumber: Int,
    @SerializedName("show_id") val showId: Int,
    @SerializedName("still_path") val stillPath: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Double,
    @ColumnInfo(name = "airstamp") var airStamp: String,
    @ColumnInfo(name = "airtime") var airTime: String,
    @ColumnInfo(name = "tv_next_episode") var tv_next_episode: Int
) : Parcelable {

    val getAirDate: String
        get() = airDate // TODO format date

    val tillAirDateDuration: String
        get() = returnDuration(airDate)


    fun formattedSeasonEpisode(): String {
        return String.format("%02dx%02d", _seasonNumber, _episodeNumber)
    }
}
