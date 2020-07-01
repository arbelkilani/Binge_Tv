package com.arbelkilani.bingetv.data.source.local.tv

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arbelkilani.bingetv.data.model.tv.TvDetails

@Dao
interface TvDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTv(tv: TvDetails)

    @Query("SELECT * FROM tv_table WHERE id=:id")
    suspend fun getTvShow(id: Int): TvDetails?
}