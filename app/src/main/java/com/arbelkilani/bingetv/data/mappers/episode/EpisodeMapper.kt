package com.arbelkilani.bingetv.data.mappers.episode

import com.arbelkilani.bingetv.data.entities.episode.EpisodeData
import com.arbelkilani.bingetv.data.mappers.base.Mapper
import com.arbelkilani.bingetv.domain.entities.episode.EpisodeEntity

class EpisodeMapper : Mapper<EpisodeEntity, EpisodeData> {

    override fun mapFromEntity(type: EpisodeEntity): EpisodeData {
        return EpisodeData(
            id = type.id,
            name = type.name,
            overview = type.overview,
            airDate = type.airDate,
            episodeNumber = type.episodeNumber,
            stillPath = type.stillPath,
            voteAverage = type.voteAverage
        )
    }

    override fun mapToEntity(type: EpisodeData): EpisodeEntity {
        return EpisodeEntity(
            id = type.id,
            name = type.name,
            overview = type.overview,
            airDate = type.airDate,
            episodeNumber = type.episodeNumber,
            stillPath = "",
            voteAverage = type.voteAverage
        )
    }
}