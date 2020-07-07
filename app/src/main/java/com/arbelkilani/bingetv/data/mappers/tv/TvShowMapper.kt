package com.arbelkilani.bingetv.data.mappers.tv

import com.arbelkilani.bingetv.data.entities.tv.TvShowData
import com.arbelkilani.bingetv.data.mappers.base.Mapper
import com.arbelkilani.bingetv.data.mappers.season.SeasonMapper
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity


class TvShowMapper : Mapper<TvShowEntity, TvShowData> {

    private val seasonMapper = SeasonMapper()

    companion object {
        private const val baseBackdrop = "https://image.tmdb.org/t/p/w780"
        private const val basePoster = "https://image.tmdb.org/t/p/w185"
    }

    override fun mapFromEntity(type: TvShowEntity): TvShowData {
        return TvShowData()
    }

    override fun mapToEntity(type: TvShowData): TvShowEntity {

        return TvShowEntity(
            id = type.id,
            name = type.name,
            overview = type.overview,
            status = type.status,
            type = type.type,
            voteAverage = type.voteAverage,
            homepage = type.homepage,
            nextEpisodeData = type.nextEpisode,
            genres = type.genres,
            networks = type.networks,
            images = type.images?.backdrops?.map { image ->
                String.format("%s%s", baseBackdrop, image.filePath)
            },
            videos = type.videos?.results?.map { video ->
                if (video.site == "YouTube") video.key else ""
            },
            seasonsCount = String.format("%d seasons", type.numberOfSeasons),
            seasons = type.seasons.map { seasonData ->
                seasonMapper.mapToEntity(seasonData)
            },
            backdrop = baseBackdrop + type.backdropPath,
            poster = basePoster + type.posterPath
        )
    }
}