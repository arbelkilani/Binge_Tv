package com.arbelkilani.bingetv.data.model.tv

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.arbelkilani.bingetv.utils.spannableVoteRate
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tv_table")
data class Tv(
    @ColumnInfo(name = "backdrop") @SerializedName("backdrop_path") var backdrop_Path: String?,
    @ColumnInfo(name = "firstAirDate") var first_air_date: String,
    @ColumnInfo(name = "genreIds") var genre_ids: List<Int>,
    @PrimaryKey var id: Int,
    var name: String,
    //@Ignore val origin_country: List<String>,
    @Ignore var original_language: String,
    @Ignore var original_name: String,
    var overview: String,
    var popularity: Double,
    @ColumnInfo(name = "poster") @SerializedName("poster_path") var poster_path: String?,
    @SerializedName("vote_average") var _voteAverage: Double,
    @SerializedName("vote_count") var voteCount: Int,
    var addWatchlist: Boolean,
    var watched: Boolean

) : Parcelable {

    constructor() : this("", "", listOf(), 0, "", "", "", "", 0.0, "", 0.0, 0, false, false)

    val posterPath: String?
        get() = "https://image.tmdb.org/t/p/w500${this.poster_path}"

    val backdropPath: String?
        get() = "https://image.tmdb.org/t/p/w780${this.backdrop_Path}"

    val voteAverage: String?
        get() = spannableVoteRate(_voteAverage.toString()) //FIXME spannable doesn't show in layout

    override fun toString(): String {
        return name
    }

}