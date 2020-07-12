package com.arbelkilani.bingetv.data.entities.credit

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Credit(
    val character: String,
    @SerializedName("credit_id")
    val creditId: String,

    val gender: Int?,

    val id: Int,

    val name: String,

    val order: Int,

    @SerializedName("profile_path")
    val profilePath: String?

) : Parcelable