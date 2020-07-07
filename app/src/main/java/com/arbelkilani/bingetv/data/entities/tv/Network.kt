package com.arbelkilani.bingetv.data.entities.tv

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
/*@Entity(
    tableName = "network_table",
    foreignKeys = [ForeignKey(
        entity = TvShowData::class,
        parentColumns = ["id"],
        childColumns = ["tv_network"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["tv_network"], name = "index_tv_network")]
)*/
data class Network(
    val name: String,
    @SerializedName("id")
    val id: Int,

    @SerializedName("logo_path")
    val logoPath: String,

    @SerializedName("origin_country")
    val originCountry: String

    //@ColumnInfo(name = "tv_network")
    //var tv_network: Int

) : Parcelable {

    override fun toString(): String {
        return name
    }
}