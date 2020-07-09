package com.arbelkilani.bingetv.data.source.local.episode

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arbelkilani.bingetv.data.entities.episode.EpisodeData

@Dao
interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEpisode(episodeData: EpisodeData)

    @Query("SELECT * FROM episode_table WHERE season_episode=:seasonId")
    suspend fun getEpisodes(seasonId: Int): List<EpisodeData>?
}