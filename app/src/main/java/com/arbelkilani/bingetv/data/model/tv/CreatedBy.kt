package com.arbelkilani.bingetv.data.model.tv

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreatedBy(
    val credit_id: String
) : Parcelable
