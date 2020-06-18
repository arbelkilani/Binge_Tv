package com.arbelkilani.bingetv.data.model.tv

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tv(
    @SerializedName("backdrop_path") val _backdropPath: String?,
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val name: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path") val _posterPath: String?,
    val vote_average: Double,
    val vote_count: Int
) : Parcelable {

    val posterPath: String?
        get() = "https://image.tmdb.org/t/p/w500${this._posterPath}"

    val backdropPath: String?
        get() = "https://image.tmdb.org/t/p/w780${this._backdropPath}"

}