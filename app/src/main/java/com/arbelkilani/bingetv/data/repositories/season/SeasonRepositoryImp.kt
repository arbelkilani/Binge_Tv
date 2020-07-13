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
        tvDao.saveTv(tvShowMapper.mapFromEntity(tvShowEntity)) // save tvShow item

        seasonEntity.watched = watched
        seasonEntity.watchedCount =
            if (watched) seasonEntity.episodeCount else 0 // this item is updated and should be returned to UI.

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

        Log.i("TAG++", "season watched = ${seasonEntity.watched}")

        val seasonLocal = seasonDao.getSeason(seasonEntity.id)
        val episodeLocal = episodeDao.getEpisodes(seasonEntity.id)

        response.watched = seasonEntity.watched
        response.watchedCount = seasonEntity.watchedCount
        response.episodes.map { remote ->
            remote.tv_episode = tvShowEntity.id
            remote.season_episode = seasonEntity.id
            episodeLocal?.apply {
                if (isEmpty()) {
                    remote.watched = seasonEntity.watched
                    episodeDao.saveEpisode(remote)
                } else {

                    map {
                        Log.i("TAG++", "it = $it")
                        if (remote.id == it.id) {
                            remote.watched = it.watched
                        }
                    }
                }
            }
        }

        //FIXME
        // user access list of episodes
        // select all episodes
        // back to seasons list -> found season checked
        // uncheck season -> should uncheck episodes
        // but if user select some episodes
        // back to season list -> found season unchecked
        // back to details ?

        Resource.success(seasonMapper.mapToEntity(response))
    } catch (e: Exception) {
        Log.e(TAG, "getSeasonDetails = ${e.localizedMessage}")
        Resource.exception(e, 0)
    }
}