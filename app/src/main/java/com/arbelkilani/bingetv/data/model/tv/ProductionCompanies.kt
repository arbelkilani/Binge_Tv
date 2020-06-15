import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductionCompanies(
    val id: Int,
    val logo_path: String,
    val name: String,
    val origin_country: String
) : Parcelable