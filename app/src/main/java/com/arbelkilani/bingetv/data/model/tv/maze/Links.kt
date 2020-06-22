package com.arbelkilani.bingetv.data.model.tv.maze

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Links(
    @SerializedName("previousepisode") val previousEpisode: PreviousEpisode?,
    @SerializedName("nextepisode") val nextEpisode: NextEpisode?
) : Parcelable