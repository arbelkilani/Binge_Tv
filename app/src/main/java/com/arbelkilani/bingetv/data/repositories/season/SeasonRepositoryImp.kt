package com.arbelkilani.bingetv.data.repositories.season

import android.util.Log
import com.arbelkilani.bingetv.data.mappers.season.SeasonMapper
import com.arbelkilani.bingetv.data.source.local.season.SeasonDao
import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity
import com.arbelkilani.bingetv.domain.repositories.SeasonRepository

class SeasonRepositoryImp(private val seasonDao: SeasonDao) : SeasonRepository {

    private val seasonMapper = SeasonMapper()

    companion object {
        private const val TAG = "SeasonRepository"
    }

    override suspend fun saveWatched(
        watched: Boolean,
        seasonEntity: SeasonEntity,
        tvShowId: Int
    ): SeasonEntity? {
        val seasonData = seasonMapper.mapFromEntity(seasonEntity)
        seasonData.watched = watched
        seasonData.tv_season = tvShowId
        try {
            seasonDao.saveSeason(seasonData)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "saveWatched : ${e.localizedMessage}")
        }

        seasonEntity.watched = watched
        seasonEntity.watchedEpisodeCount = if (watched) seasonEntity.episodeCount else 0
        seasonEntity.progress = if (watched) 100 else 0
        return seasonEntity
    }
}