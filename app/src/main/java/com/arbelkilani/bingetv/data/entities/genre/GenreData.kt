package com.arbelkilani.bingetv.data.entities.genre

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "genre_table")
data class GenreData(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Int = -1,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String = "",

    @ColumnInfo(name = "count")
    var count: Int = 0,

    var percentage: Float = 0f

) {

    fun mapOf(): Map<String, String> {
        return mapOf(
            "id" to id.toString(),
            "name" to name,
            "count" to count.toString(),
            "percentage" to percentage.toString()
        )
    }
}