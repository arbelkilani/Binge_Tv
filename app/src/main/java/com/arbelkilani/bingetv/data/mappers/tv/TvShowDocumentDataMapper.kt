package com.arbelkilani.bingetv.data.mappers.tv

import com.arbelkilani.bingetv.data.entities.tv.TvShowData
import com.arbelkilani.bingetv.data.entities.tv.TvShowDocument
import com.arbelkilani.bingetv.data.mappers.base.DocumentDataMapper

class TvShowDocumentDataMapper : DocumentDataMapper<TvShowDocument, TvShowData> {

    override fun mapFromData(type: TvShowData): TvShowDocument {
        return TvShowDocument(

        )
    }

    override fun mapFromDocument(type: TvShowDocument): TvShowData {
        return TvShowData(
            backdropPath = type.backdrop_path,
            episodeCount = type.episode_count.toInt(),
            futureEpisodesCount = type.future_episodes_count.toInt(),
            id = type.id.toInt(),
            inProduction = type.in_production.toBoolean(),
            name = type.name,
            posterPath = type.poster_path,
            runtime = type.runtime.toInt(),
            watched = type.watched.toBoolean(),
            watchedCount = type.watched_count.toInt(),
            watchlist = type.watchlist.toBoolean()
        )
    }
}