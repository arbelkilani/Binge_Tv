package com.arbelkilani.bingetv.data.repositories.profile

import com.arbelkilani.bingetv.data.source.local.tv.TvDao
import com.arbelkilani.bingetv.domain.entities.profile.StatisticsEntity
import com.arbelkilani.bingetv.domain.repositories.ProfileRepository

class ProfileRepositoryImp(
    private val tvDao: TvDao
) : ProfileRepository {

    override suspend fun getStatistics(): StatisticsEntity {
        var episodeCount = 0
        var tvShowCount = 0
        val tvShows = tvDao.getAllTvShows()
        tvShows?.apply {
            map {
                episodeCount += it.watchedCount
                if (it.watched) {
                    tvShowCount += 1
                }
            }
        }
        return StatisticsEntity(episodeCount = episodeCount, tvShowCount = tvShowCount)
    }
}