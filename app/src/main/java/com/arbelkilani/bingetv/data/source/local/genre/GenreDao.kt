package com.arbelkilani.bingetv.data.source.local.genre

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arbelkilani.bingetv.data.entities.genre.Genre

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGenres(genres: List<Genre>)

    @Query("Select * from genre_table")
    suspend fun getGenres(): List<Genre>

    suspend fun isEmpty(): Boolean = getGenres().isNullOrEmpty()

}