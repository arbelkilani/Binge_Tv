package com.arbelkilani.bingetv.data.source.local.tv

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arbelkilani.bingetv.data.model.tv.EpisodeToAir
import com.arbelkilani.bingetv.data.model.tv.TvShow

@Dao
interface TvDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTv(tv: TvShow)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNextEpisode(episodeToAir: EpisodeToAir)

    @Query("SELECT * FROM tv_table WHERE id=:id")
    suspend fun getTvShow(id: Int): TvShow?

    @Query("SELECT * FROM tv_table")
    suspend fun getAllTvShows(): List<TvShow>?
}