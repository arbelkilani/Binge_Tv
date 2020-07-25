package com.arbelkilani.bingetv.data.mappers.tv

import com.arbelkilani.bingetv.data.entities.tv.TvShowData
import com.arbelkilani.bingetv.data.mappers.base.Mapper
import com.arbelkilani.bingetv.data.mappers.genre.GenreMapper
import com.arbelkilani.bingetv.data.mappers.season.SeasonMapper
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity


class TvShowMapper : Mapper<TvShowEntity, TvShowData> {

    private val seasonMapper = SeasonMapper()
    private val genreMapper = GenreMapper()

    override fun mapFromEntity(type: TvShowEntity): TvShowData {

        return TvShowData(
            id = type.id,
            inProduction = type.inProduction,
            episodeCount = type.episodeCount,
            name = type.name,
            overview = type.overview,
            firstAirData = type.firstAirDate,
            runtime = type.runtime,
            status = type.status,
            type = type.type,
            voteAverage = type.voteAverage,
            homepage = type.homepage,
            posterPath = type.poster,
            backdropPath = type.backdrop,
            watched = type.watched,
            watchlist = type.watchlist,
            watchedCount = type.watchedCount,
            futureEpisodesCount = type.futureEpisodesCount,
            nextEpisode = type.nextEpisode
        )
    }

    override fun mapToEntity(type: TvShowData): TvShowEntity {

        return TvShowEntity(
            id = type.id,
            inProduction = type.inProduction,
            episodeCount = type.episodeCount,
            name = type.name,
            overview = type.overview,
            firstAirDate = type.firstAirData,
            runtime = if (type.episodeRunTime.isEmpty()) 0 else type.episodeRunTime[0],
            status = type.status,
            type = type.type,
            voteAverage = type.voteAverage,
            homepage = type.homepage,
            nextEpisode = type.nextEpisode,
            genres = type.genres.map { genreData ->
                genreMapper.mapToEntity(genreData)
            },
            networks = type.networks,
            //images = type.images?.backdrops?.map { image ->
            //    String.format("%s%s", baseBackdrop, image.filePath)
            //},
            videos = type.videos?.results?.map { video ->
                if (video.site == "YouTube") video.key else ""
            },
            seasonsCount = String.format("%d seasons", type.numberOfSeasons),
            seasons = type.seasons
                .filter { seasonData -> seasonData.seasonNumber > 0 }
                .map { seasonData ->
                    seasonMapper.mapToEntity(seasonData)
                },
            backdrop = type.backdropPath,
            poster = type.posterPath,
            watched = type.watched,
            watchlist = type.watchlist,
            watchedCount = type.watchedCount,
            futureEpisodesCount = type.futureEpisodesCount
        )
    }
}