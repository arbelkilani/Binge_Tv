package com.arbelkilani.bingetv.data.model.tv

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "network_table")
data class Network(
    val name: String,
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "logo_path") @SerializedName("logo_path") val logoPath: String,
    @ColumnInfo(name = "origin_country") @SerializedName("origin_country") val originCountry: String
) : Parcelable {

    val getLogoPath: String?
        get() = "https://image.tmdb.org/t/p/w342${this.logoPath}"

    override fun toString(): String {
        return name
    }
}