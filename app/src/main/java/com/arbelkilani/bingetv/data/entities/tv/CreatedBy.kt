package com.arbelkilani.bingetv.data.entities.tv

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreatedBy(
    val id : Int,
    val credit_id: String,
    val name : String,
    val gender : Int,
    val profile_path : String
) : Parcelable
