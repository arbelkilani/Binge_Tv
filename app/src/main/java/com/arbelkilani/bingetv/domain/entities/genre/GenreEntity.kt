package com.arbelkilani.bingetv.domain.entities.genre

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenreEntity(
    var id: Int = -1,
    var name: String = "",
    var count: Int = 0,
    var percentage: Int = 0
) : Parcelable