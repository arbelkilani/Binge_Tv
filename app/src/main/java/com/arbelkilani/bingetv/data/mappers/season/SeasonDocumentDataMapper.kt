package com.arbelkilani.bingetv.data.mappers.season

import com.arbelkilani.bingetv.data.entities.season.SeasonData
import com.arbelkilani.bingetv.data.entities.season.SeasonDocument
import com.arbelkilani.bingetv.data.mappers.base.DocumentDataMapper

class SeasonDocumentDataMapper : DocumentDataMapper<SeasonDocument, SeasonData> {

    override fun mapFromData(type: SeasonData): SeasonDocument {
        return SeasonDocument(

        )
    }

    override fun mapFromDocument(type: SeasonDocument): SeasonData {
        return SeasonData(
            id = type.id.toInt(),
            episodeCount = type.episode_count.toInt(),
            seasonNumber = type.season_number.toInt(),
            tv_season = type.season_number.toInt(),
            watched = type.watched.toBoolean(),
            watchedCount = type.watched_count.toInt(),
            futureEpisodeCount = type.future_episode_count.toInt()
        )
    }
}