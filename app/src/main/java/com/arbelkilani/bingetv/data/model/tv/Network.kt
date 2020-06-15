package com.arbelkilani.bingetv.data.model.tv

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Network(
    val name: String,
    val id: Int,
    @SerializedName("logo_path") val logoPath: String,
    @SerializedName("origin_country") val originCountry: String
) : Parcelable