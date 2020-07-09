package com.arbelkilani.bingetv.data.repositories.episode

import android.util.Log
import com.arbelkilani.bingetv.data.mappers.episode.EpisodeMapper
import com.arbelkilani.bingetv.data.mappers.season.SeasonMapper
import com.arbelkilani.bingetv.data.mappers.tv.TvShowMapper
import com.arbelkilani.bingetv.data.source.local.episode.EpisodeDao
import com.arbelkilani.bingetv.data.source.local.season.SeasonDao
import com.arbelkilani.bingetv.domain.entities.episode.EpisodeEntity
import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.domain.repositories.EpisodeRepository

class EpisodeRepositoryImp(
    private val episodeDao: EpisodeDao,
    private val seasonDao: SeasonDao
) : EpisodeRepository {

    private val episodeMapper = EpisodeMapper()
    private val seasonMapper = SeasonMapper()
    private val tvShowMapper = TvShowMapper()

    companion object {
        private const val TAG = "EpisodeRepository"
    }

    override suspend fun saveWatched(
        watched: Boolean,
        episodeEntity: EpisodeEntity,
        tvShowEntity: TvShowEntity,
        seasonEntity: SeasonEntity
    ): EpisodeEntity? {

        //FIXME watched always return true

        val episodeData = episodeMapper.mapFromEntity(episodeEntity)
        episodeData.watched = watched
        episodeData.tv_episode = tvShowEntity.id
        episodeData.season_episode = seasonEntity.id

        try {
            episodeDao.saveEpisode(episodeData)
            updateTables(tvShowEntity, seasonEntity, watched)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "save episode = ${e.localizedMessage}")
        }

        episodeEntity.watched = watched

        return episodeEntity
    }

    private suspend fun updateTables(
        tvShowEntity: TvShowEntity,
        seasonEntity: SeasonEntity,
        watched: Boolean
    ) {
        updateRelatedSeason(seasonEntity, watched, tvShowEntity)

        val tvShowData = tvShowMapper.mapFromEntity(tvShowEntity)

        //FIXME once user clicked on last episode in this season tv should be notified that a hole season has been watched
        // to do this we compare watched count to episode count


    }

    private suspend fun updateRelatedSeason(
        seasonEntity: SeasonEntity,
        watched: Boolean,
        tvShowEntity: TvShowEntity
    ) {
        val seasonData = seasonMapper.mapFromEntity(seasonEntity)
        val localSeasonData = seasonDao.getSeason(seasonEntity.id)

        localSeasonData?.let {
            seasonData.watchedCount = it.watchedCount
        }

        if (watched) {
            seasonData.watchedCount++
        } else {
            seasonData.watchedCount--
        }

        //FIXME
        // while decreasing episode watched watched value is set to false despite that seen episodes still exists in database

        seasonData.watched = watched
        seasonData.tv_season = tvShowEntity.id

        try {
            seasonDao.saveSeason(seasonData)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "update tables = ${e.localizedMessage}")
        }
    }


}