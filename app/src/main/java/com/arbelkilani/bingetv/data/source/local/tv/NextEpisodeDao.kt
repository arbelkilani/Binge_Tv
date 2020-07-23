package com.arbelkilani.bingetv.data.source.local.tv

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.arbelkilani.bingetv.data.entities.tv.maze.details.NextEpisodeData

@Dao
interface NextEpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNextEpisode(nextEpisodeData: NextEpisodeData)
}