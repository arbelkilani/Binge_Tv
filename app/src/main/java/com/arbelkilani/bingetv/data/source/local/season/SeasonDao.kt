package com.arbelkilani.bingetv.data.source.local.season

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arbelkilani.bingetv.data.entities.season.SeasonData

@Dao
interface SeasonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSeason(seasonData: SeasonData)

    @Query("SELECT * FROM season_table WHERE tv_season=:id")
    fun getSeasons(id: Int): List<SeasonData>?

    @Query("SELECT * FROM season_table WHERE id=:seasonId")
    fun getSeason(seasonId: Int): SeasonData?

    @Query("SELECT * FROM season_table")
    suspend fun getAllSeasons(): List<SeasonData>?
}
