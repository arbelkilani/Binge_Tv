package com.arbelkilani.bingetv.data.repositories.season

import android.util.Log
import com.arbelkilani.bingetv.data.entities.base.Resource
import com.arbelkilani.bingetv.data.mappers.episode.EpisodeMapper
import com.arbelkilani.bingetv.data.mappers.season.SeasonMapper
import com.arbelkilani.bingetv.data.mappers.tv.TvShowMapper
import com.arbelkilani.bingetv.data.source.local.episode.EpisodeDao
import com.arbelkilani.bingetv.data.source.local.season.SeasonDao
import com.arbelkilani.bingetv.data.source.local.tv.TvDao
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTmdbService
import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.domain.repositories.SeasonRepository

class SeasonRepositoryImp(
    private val apiTmdbService: ApiTmdbService,
    private val seasonDao: SeasonDao,
    private val episodeDao: EpisodeDao,
    private val tvDao: TvDao
) : SeasonRepository {

    private val seasonMapper = SeasonMapper()
    private val tvShowMapper = TvShowMapper()
    private val episodeMapper = EpisodeMapper()

    companion object {
        private const val TAG = "SeasonRepository"
    }

    override suspend fun saveWatched(
        watched: Boolean,
        seasonEntity: SeasonEntity,
        tvShowEntity: TvShowEntity
    ): SeasonEntity? {

        tvShowEntity.seasons.map {
            if (it.id == seasonEntity.id) {
                it.watched = watched
                it.watchedCount = if (watched) it.episodeCount else 0
                val seasonData = seasonMapper.mapFromEntity(it)
                seasonData.tv_season = tvShowEntity.id
                seasonDao.saveSeason(seasonData)
            }
        }
        // map seasons in tvShowEntity in order to update parameters watched and watchCount
        // create season data item and save it to database
        var count = 0
        tvShowEntity.seasons.map {
            if (it.watched)
                count++
            else
                count--
        } // calculate seasons count set as watched to compare to seasons size and update tvShow watched state

        tvShowEntity.watched = count == tvShowEntity.seasons.size

        seasonEntity.watched = watched
        seasonEntity.watchedCount =
            if (watched) seasonEntity.episodeCount else 0 // this item is updated and should be returned to UI.

        val localTvShow = tvDao.getTvShow(tvShowEntity.id) // get local tvShow
        var tvShowWatchedCount = 0
        localTvShow?.apply {
            tvShowWatchedCount = watchedCount
        }

        var selectedEpisodesCount: Int =
            0 // use case where some episodes are selected inside the season.
        val localEpisodes = episodeDao.getEpisodes(seasonEntity.id)
        selectedEpisodesCount = localEpisodes?.filter { it.watched }!!.size

        if (watched) { // update tv show watched count depending either season is set as watched or not.
            tvShowWatchedCount += seasonEntity.episodeCount - selectedEpisodesCount
        } else {
            tvShowWatchedCount -= seasonEntity.episodeCount
        }

        tvShowEntity.watchedCount = tvShowWatchedCount

        tvDao.saveTv(tvShowMapper.mapFromEntity(tvShowEntity)) // save tvShow item

        return seasonEntity
    }

    override suspend fun getSeasonDetails(
        tvShowEntity: TvShowEntity,
        seasonEntity: SeasonEntity
    ): Resource<SeasonEntity> = try {

        val response = apiTmdbService.getSeasonDetails(
            tvId = tvShowEntity.id,
            seasonNumber = seasonEntity.seasonNumber
        )

        val seasonLocal = seasonDao.getSeason(seasonEntity.id)
        val episodeLocal = episodeDao.getEpisodes(seasonEntity.id)

        seasonLocal?.apply {
            seasonEntity.watched =
                watchedCount == episodeCount // set watched state depending on watch count.
        }

        response.watched = seasonEntity.watched
        response.watchedCount = seasonEntity.watchedCount
        response.episodes.map { remote -> // map remote data episodes to create episode data
            remote.tv_episode = tvShowEntity.id
            remote.season_episode = seasonEntity.id
            episodeLocal?.apply { // no episode state has been selected before - no episode in DB
                if (isEmpty()) {
                    if (remote.episodeNumber in 1..seasonEntity.watchedCount) {
                        remote.watched = seasonEntity.watchedCount > 0
                    }
                    episodeDao.saveEpisode(remote)
                } else { // episodes exists in DB so we need to keep there states.
                    map {
                        if (seasonEntity.watchedCount == 0 || seasonEntity.watched)
                            remote.watched =
                                seasonEntity.watched // case where user set season as Watched/Not watched
                        else {
                            if (remote.id == it.id) { // case where we keep track on saved data for each episode.
                                remote.watched = it.watched
                            }
                        }
                    }
                }
            }
        }

        Resource.success(seasonMapper.mapToEntity(response))
    } catch (e: Exception) {
        Log.e(TAG, "getSeasonDetails = ${e.localizedMessage}")
        Resource.exception(e, 0)
    }
}