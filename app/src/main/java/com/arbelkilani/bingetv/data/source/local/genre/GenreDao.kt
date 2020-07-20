package com.arbelkilani.bingetv.data.source.local.genre

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arbelkilani.bingetv.data.entities.genre.GenreData

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGenre(genreData: GenreData)

    @Query("UPDATE genre_table SET count = count + :increment WHERE id = :genreId")
    suspend fun incrementCount(genreId: Int, increment: Int)

    @Query("SELECT * FROM genre_table ORDER BY count DESC")
    suspend fun getGenres(): List<GenreData>

}