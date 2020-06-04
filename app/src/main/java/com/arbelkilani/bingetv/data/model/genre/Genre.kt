package com.arbelkilani.bingetv.data.model.genre

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "genre_table")
data class Genre(

    @PrimaryKey
    @SerializedName("id")
    var id: Int,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String
)
