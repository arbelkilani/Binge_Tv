package com.arbelkilani.bingetv.data.source.local.genre

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.arbelkilani.bingetv.data.entities.genre.GenreData

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGenre(genreData: GenreData)
}
