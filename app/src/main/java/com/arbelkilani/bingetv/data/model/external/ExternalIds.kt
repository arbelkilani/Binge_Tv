package com.arbelkilani.bingetv.data.model.external

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExternalIds(
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("freebase_mid") val freebaseMid: String?,
    @SerializedName("freebase_id") val freebaseId: String?,
    @SerializedName("tvdb_id") val tvdbId: Int?,
    @SerializedName("tvrage_id") val tvrageId: Int?,
    @SerializedName("facebook_id") val facebookId: String?,
    @SerializedName("instagram_id") val instagramId: String?,
    @SerializedName("twitter_id") val twitterId: String?,
    val id: Int
) : Parcelable