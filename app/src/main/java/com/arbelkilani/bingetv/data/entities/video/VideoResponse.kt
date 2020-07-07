package com.arbelkilani.bingetv.data.entities.video

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoResponse(
    val results: List<Video>
) : Parcelable
