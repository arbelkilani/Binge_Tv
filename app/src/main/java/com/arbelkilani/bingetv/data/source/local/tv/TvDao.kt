package com.arbelkilani.bingetv.data.source.local.tv

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arbelkilani.bingetv.data.entities.tv.TvShowData

@Dao
interface TvDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTv(tv: TvShowData)

    @Query("SELECT * FROM tv_table WHERE id=:id")
    suspend fun getTvShow(id: Int): TvShowData?

    @Query("SELECT * FROM tv_table")
    suspend fun getAllTvShows(): List<TvShowData>?

    @Query("SELECT * FROM tv_table WHERE watchlist=:state")
    suspend fun watchlist(state: Boolean): List<TvShowData>?

    @Query("SELECT * FROM tv_table WHERE watched_count>'0'")
    suspend fun watched(): List<TvShowData>?

}