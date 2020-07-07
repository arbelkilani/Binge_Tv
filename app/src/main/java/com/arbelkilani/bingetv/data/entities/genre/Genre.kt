package com.arbelkilani.bingetv.data.entities.genre

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
/*@Entity(
    tableName = "genre_table",
    foreignKeys = [ForeignKey(
        entity = TvShowData::class,
        parentColumns = ["id"],
        childColumns = ["tv_genre"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["tv_genre"], name = "index_tv_genre")]
)*/
data class Genre(

    //@PrimaryKey(autoGenerate = true) var key: Long,
    @SerializedName("id")
    var id: Int,

    //@ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String

    //@ColumnInfo(name = "tv_genre")
    //var tv_genre: Int
) : Parcelable {
    override fun toString(): String {
        return name
    }
}
