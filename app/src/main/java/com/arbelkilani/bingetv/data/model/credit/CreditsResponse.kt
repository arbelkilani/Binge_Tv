package com.arbelkilani.bingetv.data.model.credit

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreditsResponse(
    val cast: List<Credit>,
    val id: Int
) : Parcelable