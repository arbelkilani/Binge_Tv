import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvDetails(
    val backdrop_path: String,
    val created_by: List<CreatedBy>,
    val episode_run_time: List<Int>,
    val first_air_date: String,
    val genres: List<Genres>,
    val homepage: String,
    val id: Int,
    val in_production: Boolean,
    val languages: List<String>,
    val last_air_date: String,
    val lastEpisodeToAir: LastEpisodeToAir,
    val name: String,
    val next_episode_to_air: NextEpisodeToAir,
    val networks: List<Networks>,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompanies>,
    val seasons: List<Seasons>,
    val status: String,
    val type: String,
    val vote_average: Double,
    val vote_count: Int
) : Parcelable