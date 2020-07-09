package com.arbelkilani.bingetv.data.source.local.episode

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.arbelkilani.bingetv.data.entities.episode.EpisodeData

@Dao
interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEpisode(episodeData: EpisodeData)
}