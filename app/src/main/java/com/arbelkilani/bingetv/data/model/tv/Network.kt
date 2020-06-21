package com.arbelkilani.bingetv.data.model.tv

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Network(
    val name: String,
    val id: Int,
    @SerializedName("logo_path") val _logoPath: String,
    @SerializedName("origin_country") val originCountry: String
) : Parcelable {
    val logoPath: String?
        get() = "https://image.tmdb.org/t/p/w342${this._logoPath}"

    override fun toString(): String {
        return name
    }
}