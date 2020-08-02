package com.arbelkilani.bingetv.data.entities.tv

data class TvShowDocument(
    var backdrop_path: String = "",
    var episode_count: String = "",
    var future_episodes_count: String = "",
    var id: String = "",
    var in_production: String = "",
    var name: String = "",
    var poster_path: String = "",
    var runtime: String = "",
    var watched: String = "",
    var watched_count: String = "",
    var watchlist: String = ""
) {
    fun fromDocumentToData(): TvShowData {
        return TvShowData(
            backdropPath = backdrop_path,
            episodeCount = episode_count.toInt(),
            futureEpisodesCount = future_episodes_count.toInt(),
            id = id.toInt(),
            inProduction = in_production.toBoolean(),
            name = name,
            posterPath = poster_path,
            runtime = runtime.toInt(),
            watched = watched.toBoolean(),
            watchedCount = watched_count.toInt(),
            watchlist = watchlist.toBoolean()
        )
    }
}
