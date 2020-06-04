package com.arbelkilani.bingetv.data.source.local.genre

import androidx.room.*
import com.arbelkilani.bingetv.data.model.genre.Genre

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGenres(genres: List<Genre>)

    @Query("Select * from genre_table")
    suspend fun getGenres(): List<Genre>

    suspend fun isEmpty(): Boolean = getGenres().isNullOrEmpty()

}