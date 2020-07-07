package com.arbelkilani.bingetv.data.mappers.season

import com.arbelkilani.bingetv.data.entities.season.SeasonData
import com.arbelkilani.bingetv.data.mappers.base.Mapper
import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity

class SeasonMapper : Mapper<SeasonEntity, SeasonData> {

    companion object {
        private const val baseBackdrop = "https://image.tmdb.org/t/p/w780"
        private const val basePoster = "https://image.tmdb.org/t/p/w185"
    }

    override fun mapFromEntity(type: SeasonEntity): SeasonData {
        return SeasonData()
    }

    override fun mapToEntity(type: SeasonData): SeasonEntity {
        return SeasonEntity(
            id = type.id,
            episodeCount = type.episodeCount,
            name = type.name,
            overview = type.overview,
            poster = String.format("%s%s", basePoster, type.posterPath)
        )
    }
}