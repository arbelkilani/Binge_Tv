package com.arbelkilani.bingetv.data.repositories.episode

import android.util.Log
import com.arbelkilani.bingetv.data.mappers.episode.EpisodeMapper
import com.arbelkilani.bingetv.data.source.local.episode.EpisodeDao
import com.arbelkilani.bingetv.data.source.local.season.SeasonDao
import com.arbelkilani.bingetv.domain.entities.episode.EpisodeEntity
import com.arbelkilani.bingetv.domain.repositories.EpisodeRepository

class EpisodeRepositoryImp(
    private val episodeDao: EpisodeDao,
    private val seasonDao: SeasonDao
) : EpisodeRepository {

    private val episodeMapper = EpisodeMapper()

    companion object {
        private const val TAG = "EpisodeRepository"
    }

    override suspend fun saveWatched(
        watched: Boolean,
        episodeEntity: EpisodeEntity,
        tvShowId: Int,
        seasonId: Int
    ): EpisodeEntity? {

        val episodeData = episodeMapper.mapFromEntity(episodeEntity)
        episodeData.watched = watched
        episodeData.tv_episode = tvShowId
        episodeData.season_episode = seasonId

        try {
            episodeDao.saveEpisode(episodeData)
            updateRelatedSeason(seasonId, watched)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "save episode = ${e.localizedMessage}")
        }


        //episodeDao.saveEpisode(episodeMapper.mapFromEntity(episodeEntity))
        //episodeEntity.name = "zabour"
        return episodeEntity
    }

    private fun updateRelatedSeason(seasonId: Int, watched: Boolean) {
        // if episode is watched increment watched count else decrement

    }


}