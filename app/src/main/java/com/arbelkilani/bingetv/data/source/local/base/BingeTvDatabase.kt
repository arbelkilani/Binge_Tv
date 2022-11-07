package com.arbelkilani.bingetv.data.source.local.base

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arbelkilani.bingetv.data.entities.episode.EpisodeData
import com.arbelkilani.bingetv.data.entities.genre.GenreData
import com.arbelkilani.bingetv.data.entities.season.SeasonData
import com.arbelkilani.bingetv.data.entities.tv.TvShowData
import com.arbelkilani.bingetv.data.entities.tv.maze.details.NextEpisodeData
import com.arbelkilani.bingetv.data.source.local.Converters
import com.arbelkilani.bingetv.data.source.local.episode.EpisodeDao
import com.arbelkilani.bingetv.data.source.local.genre.GenreDao
import com.arbelkilani.bingetv.data.source.local.season.SeasonDao
import com.arbelkilani.bingetv.data.source.local.tv.NextEpisodeDao
import com.arbelkilani.bingetv.data.source.local.tv.TvDao

@Database(
    entities = [TvShowData::class, SeasonData::class, EpisodeData::class, GenreData::class, NextEpisodeData::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BingeTvDatabase : RoomDatabase() {
    abstract fun getTvDao(): TvDao
    abstract fun getSeasonDao(): SeasonDao
    abstract fun getEpisodeDao(): EpisodeDao
    abstract fun getGenreDao(): GenreDao
    abstract fun getNextEpisodeDao(): NextEpisodeDao
}