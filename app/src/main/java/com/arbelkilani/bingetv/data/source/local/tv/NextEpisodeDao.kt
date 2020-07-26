package com.arbelkilani.bingetv.data.source.local.tv

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arbelkilani.bingetv.data.entities.tv.maze.details.NextEpisodeData

@Dao
interface NextEpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNextEpisode(nextEpisodeData: NextEpisodeData)

    @Query("SELECT * FROM next_episode_table WHERE tv_next_episode=:tvShowId")
    suspend fun getNextEpisode(tvShowId: Int): NextEpisodeData?
}