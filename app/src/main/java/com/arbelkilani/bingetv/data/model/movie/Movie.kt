package com.arbelkilani.bingetv.data.model.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val poster_path: String?,
    val adult: Boolean,
    val overview: String,
    val release_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_title: String,
    val original_language: String,
    val title: String,
    val backdrop_path: String?,
    val popularity: Double,
    val vote_count: Int,
    val video: Boolean,
    val vote_average: Double
) : Parcelable {
    fun getPosterPath(): String {
        return "https://image.tmdb.org/t/p/w500$poster_path"
    }

    fun getBackdropPath(): String {
        return "https://image.tmdb.org/t/p/w780$backdrop_path"
    }
}