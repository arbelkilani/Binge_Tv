package com.arbelkilani.bingetv.data.source.local.season

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.arbelkilani.bingetv.data.model.season.Season

@Dao
interface SeasonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSeason(season: Season)
}
