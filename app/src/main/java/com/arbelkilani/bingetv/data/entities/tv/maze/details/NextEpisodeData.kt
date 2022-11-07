package com.arbelkilani.bingetv.data.entities.tv.maze.details

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "next_episode_table")
data class NextEpisodeData(

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String = "",

    @ColumnInfo(name = "season")
    @SerializedName("season")
    var season: Int = 0,

    @ColumnInfo(name = "number")
    @SerializedName("number")
    var number: Int = 0,

    @Ignore
    @SerializedName("airdate")
    var airDate: String? = "",

    @Ignore
    @SerializedName("airstamp")
    var airStamp: String = "",

    @Ignore
    @SerializedName("airtime")
    var airTime: String = "",

    @ColumnInfo(name = "summary")
    @SerializedName("summary")
    var summary: String? = null,

    @ColumnInfo(name = "time")
    var time: Long = 0L,

    @PrimaryKey
    @ColumnInfo(name = "tv_next_episode")
    var tv_next_episode: Int = -1

) : Parcelable {

    fun mapOf(): Map<String, String> {
        return mapOf(
            "name" to name,
            "season" to season.toString(),
            "number" to number.toString(),
            "summary" to summary.toString(),
            "time" to time.toString(),
            "tv_next_episode" to tv_next_episode.toString()
        )
    }
}