package com.arbelkilani.bingetv.data.repositories.profile

import com.arbelkilani.bingetv.data.entities.profile.StatisticsData
import com.arbelkilani.bingetv.data.mappers.StatisticsMapper
import com.arbelkilani.bingetv.data.mappers.genre.GenreMapper
import com.arbelkilani.bingetv.data.source.local.genre.GenreDao
import com.arbelkilani.bingetv.data.source.local.tv.TvDao
import com.arbelkilani.bingetv.domain.entities.genre.GenreEntity
import com.arbelkilani.bingetv.domain.entities.profile.StatisticsEntity
import com.arbelkilani.bingetv.domain.repositories.ProfileRepository

class ProfileRepositoryImp(
    private val tvDao: TvDao,
    private val genreDao: GenreDao
) : ProfileRepository {

    private val statisticsMapper = StatisticsMapper()
    private val genreMapper = GenreMapper()

    override suspend fun getStatistics(): StatisticsEntity {
        var episodeCount = 0
        var tvShowCount = 0
        var totalSpentTime = 0
        val tvShows = tvDao.getAllTvShows()
        tvShows?.apply {
            map {
                episodeCount += it.watchedCount
                if (it.watchedCount > 0) {
                    tvShowCount += 1
                    totalSpentTime += (it.runtime * it.watchedCount)
                }
            }
        }

        val statisticsData = StatisticsData(episodeCount, tvShowCount, totalSpentTime)
        return statisticsMapper.mapToEntity(statisticsData)
    }

    override suspend fun getGenres(): List<GenreEntity> {
        return genreDao.getGenres().let {
            it.map { genreData ->
                genreData.percentage = genreData.count * 100 / it.size
                genreMapper.mapToEntity(genreData)
            }
        }
    }
}