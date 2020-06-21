package com.arbelkilani.bingetv.data.model.genre

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "genre_table")
data class Genre(

    @PrimaryKey
    @SerializedName("id")
    var id: Int,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String
) : Parcelable {
    override fun toString(): String {
        return name
    }
}
