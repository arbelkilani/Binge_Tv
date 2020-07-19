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
    var id: Int,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String

)