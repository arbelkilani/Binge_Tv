package com.arbelkilani.bingetv.data.entities.credit

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Credit(
    val character: String,
    @SerializedName("credit_id") val creditId: String,
    val gender: Int?,
    val id: Int,
    val name: String,
    val order: Int,
    @SerializedName("profile_path") val _profilePath: String?
) : Parcelable {

    val profilePath: String?
        get() = "https://image.tmdb.org/t/p/w185${this._profilePath}"
}