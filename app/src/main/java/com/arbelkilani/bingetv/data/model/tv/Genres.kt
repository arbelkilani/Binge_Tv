import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genres(
    val id: Int,
    val name: String
) : Parcelable