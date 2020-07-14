package com.arbelkilani.bingetv.data.mappers.season

import com.arbelkilani.bingetv.data.entities.season.SeasonData
import com.arbelkilani.bingetv.data.mappers.base.Mapper
import com.arbelkilani.bingetv.data.mappers.episode.EpisodeMapper
import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity

class SeasonMapper : Mapper<SeasonEntity, SeasonData> {

    private val episodeMapper = EpisodeMapper()

    override fun mapFromEntity(type: SeasonEntity): SeasonData {
        return SeasonData(
            id = type.id,
            seasonNumber = type.seasonNumber,
            episodeCount = type.episodeCount,
            name = type.name,
            overview = type.overview,
            airDate = type.airDate,
            posterPath = type.poster,
            watched = type.watched,
            watchedCount = type.watchedCount,
            futureEpisodeCount = type.futureEpisodeCount
        )
    }

    override fun mapToEntity(type: SeasonData): SeasonEntity {
        return SeasonEntity(
            id = type.id,
            seasonNumber = type.seasonNumber,
            episodeCount = type.episodeCount,
            name = type.name,
            overview = type.overview,
            airDate = type.airDate,
            poster = type.posterPath,
            watched = type.watched,
            watchedCount = type.watchedCount,
            episodes = type.episodes.map { episodeMapper.mapToEntity(it) },
            futureEpisodeCount = type.futureEpisodeCount
        )
    }
}