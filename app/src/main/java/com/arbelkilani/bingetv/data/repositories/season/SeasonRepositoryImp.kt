package com.arbelkilani.bingetv.data.repositories.season

import android.util.Log
import com.arbelkilani.bingetv.data.entities.base.Resource
import com.arbelkilani.bingetv.data.mappers.season.SeasonMapper
import com.arbelkilani.bingetv.data.source.local.season.SeasonDao
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTmdbService
import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.domain.repositories.SeasonRepository

class SeasonRepositoryImp(
    private val apiTmdbService: ApiTmdbService,
    private val seasonDao: SeasonDao
) : SeasonRepository {

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

    override suspend fun getSeasonDetails(
        tvShowEntity: TvShowEntity,
        seasonEntity: SeasonEntity
    ): Resource<SeasonEntity> = try {
        val response = apiTmdbService.getSeasonDetails(
            tvId = tvShowEntity.id,
            seasonNumber = seasonEntity.seasonNumber
        )
        val seasonLocal = seasonDao.getSeason(seasonEntity.id)
        seasonLocal?.let {
            response.watched = it.watched
        }
        Resource.success(seasonMapper.mapToEntity(response))
    } catch (e: Exception) {
        Log.e(TAG, "getSeasonDetails = ${e.localizedMessage}")
        Resource.exception(e, 0)
    }
}