package com.arbelkilani.bingetv.data.source.local.base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arbelkilani.bingetv.data.model.genre.Genre
import com.arbelkilani.bingetv.data.source.local.genre.GenreDao

@Database(entities = [Genre::class], version = 1)
abstract class BingeTvDatabase : RoomDatabase() {
    abstract fun getGenreDao() : GenreDao
}