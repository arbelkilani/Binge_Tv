package com.arbelkilani.bingetv.data.model.credit

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreditsResponse(
    val cast: List<Cast>,
    val id: Int
) : Parcelable