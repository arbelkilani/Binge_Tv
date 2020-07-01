package com.arbelkilani.bingetv.data.model.season

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "season_table")
data class Season(
    @ColumnInfo(name = "air_date") @SerializedName("air_date") val airDate: String,
    @ColumnInfo(name = "episode_count") @SerializedName("episode_count") val _episodeCount: Int,
    @PrimaryKey val id: Int,
    val name: String,
    val overview: String,
    @ColumnInfo(name = "poster_path") @SerializedName("poster_path") val posterPath: String,
    @ColumnInfo(name = "season_number") @SerializedName("season_number") val seasonNumber: Int
) : Parcelable {

    val getPosterPath: String?
        get() = "https://image.tmdb.org/t/p/w500${this.posterPath}"

    val posterPath185: String?
        get() = "https://image.tmdb.org/t/p/w185${this.posterPath}"

    val getSeasonNumber: String
        get() = String.format("%s : %d", "Season", seasonNumber, Locale.getDefault())

    val getEpisodeCount: String
        get() = String.format("%d %s ", _episodeCount, "Episodes", Locale.getDefault())

    val getAirDate: String
        get() = String.format("First aired %s ", airDate, Locale.getDefault())

    val watchedCount: String
        get() = String.format("%d/%d", 0, _episodeCount, Locale.getDefault())
}
