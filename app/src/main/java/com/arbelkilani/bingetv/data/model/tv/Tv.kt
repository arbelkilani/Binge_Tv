package com.arbelkilani.bingetv.data.model.tv

import android.os.Parcelable
import android.text.SpannableString
import com.arbelkilani.bingetv.utils.spannableVoteRate
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

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
    @SerializedName("vote_average") val _voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
) : Parcelable {

    val posterPath: String?
        get() = "https://image.tmdb.org/t/p/w500${this._posterPath}"

    val backdropPath: String?
        get() = "https://image.tmdb.org/t/p/w780${this._backdropPath}"

    val voteAverage: String?
        get() = spannableVoteRate(_voteAverage.toString()) //FIXME spannable doesn't show in layout

}