package com.arbelkilani.bingetv.data.entities.season

data class SeasonDocument(
    var id: String = "",
    var episode_count: String = "",
    var season_number: String = "",
    var tv_season: String = "",
    var watched: String = "",
    var watched_count: String = "",
    var future_episode_count: String = ""
)