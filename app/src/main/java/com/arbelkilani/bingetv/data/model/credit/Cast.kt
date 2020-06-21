package com.arbelkilani.bingetv.data.model.credit

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cast(
    val character: String,
    @SerializedName("credit_id") val creditId: String,
    val gender: Int?,
    val id: Int,
    val name: String,
    val order: Int,
    @SerializedName("profile_path") val profilePath: String?
) : Parcelable