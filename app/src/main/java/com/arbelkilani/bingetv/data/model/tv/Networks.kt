import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Networks(
    val name: String,
    val id: Int,
    val logo_path: String,
    val origin_country: String
) : Parcelable