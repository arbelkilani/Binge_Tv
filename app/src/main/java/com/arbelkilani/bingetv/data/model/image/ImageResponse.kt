package com.arbelkilani.bingetv.data.model.image

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageResponse(
    val backdrops : List<Image>,
    val posters : List<Image>
) : Parcelable
