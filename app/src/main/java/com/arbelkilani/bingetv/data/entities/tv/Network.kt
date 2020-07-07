package com.arbelkilani.bingetv.data.entities.tv

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "network_table",
    foreignKeys = [ForeignKey(
        entity = TvShowData::class,
        parentColumns = ["id"],
        childColumns = ["tv_network"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["tv_network"], name = "index_tv_network")]
)
data class Network(
    val name: String,
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "logo_path")
    @SerializedName("logo_path")
    val logoPath: String,

    @ColumnInfo(name = "origin_country")
    @SerializedName("origin_country")
    val originCountry: String,

    @ColumnInfo(name = "tv_network")
    var tv_network: Int

) : Parcelable {

    override fun toString(): String {
        return name
    }
}