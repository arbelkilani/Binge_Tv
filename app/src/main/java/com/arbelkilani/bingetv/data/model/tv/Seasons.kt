import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Seasons(
    val air_date: String,
    val episode_count: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String,
    val season_number: Int
) : Parcelable