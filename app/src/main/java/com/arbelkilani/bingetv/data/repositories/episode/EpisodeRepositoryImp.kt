package com.arbelkilani.bingetv.data.repositories.episode

import android.util.Log
import com.arbelkilani.bingetv.data.mappers.episode.EpisodeMapper
import com.arbelkilani.bingetv.data.mappers.season.SeasonMapper
import com.arbelkilani.bingetv.data.mappers.tv.TvShowMapper
import com.arbelkilani.bingetv.data.source.local.episode.EpisodeDao
import com.arbelkilani.bingetv.data.source.local.genre.GenreDao
import com.arbelkilani.bingetv.data.source.local.season.SeasonDao
import com.arbelkilani.bingetv.data.source.local.tv.TvDao
import com.arbelkilani.bingetv.domain.entities.episode.EpisodeEntity
import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.domain.repositories.EpisodeRepository

class EpisodeRepositoryImp(
    private val episodeDao: EpisodeDao,
    private val seasonDao: SeasonDao,
    private val tvDao: TvDao,
    private val genreDao: GenreDao
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
        val localTvShowData = tvDao.getTvShow(tvShowEntity.id)

        var watchedCount = tvShowData.watchedCount
        localTvShowData?.let {
            watchedCount = it.watchedCount
        }

        if (watched) {
            watchedCount++
        } else {
            watchedCount--
        }

        tvShowData.watchedCount = watchedCount

        // update for the liveData
        tvShowEntity.watchedCount = watchedCount

        // for this tv show clicking on episode is the first action
        if (localTvShowData == null) {
            tvShowEntity.genres.map {
                genreDao.incrementCount(it.id, 1)
            }
        } else {

            // tv show already exists in database -> genre already incremented
            // at this step check of clicking on episode set watched to count to zero the decrement by -1
            if (watchedCount == 0) {
                tvShowEntity.genres.map {
                    genreDao.incrementCount(it.id, -1)
                }
            }

            // if user has removed all episodes and decide to re-add one
            // tv show entity is already saved
            // then check if user clicked on watched true with watched count == 1 else it will increment when downsize watched count to 1
            if (watchedCount == 1 && watched) {
                tvShowEntity.genres.map {
                    genreDao.incrementCount(it.id, 1)
                }
            }

        }

        try {
            tvDao.saveTv(tvShowData)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "update tv table = ${e.localizedMessage}")
        }

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


        seasonData.watched = seasonData.watchedCount == seasonEntity.episodeCount
        seasonData.tv_season = tvShowEntity.id

        try {
            seasonDao.saveSeason(seasonData)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "update season table = ${e.localizedMessage}")
        }
    }


}