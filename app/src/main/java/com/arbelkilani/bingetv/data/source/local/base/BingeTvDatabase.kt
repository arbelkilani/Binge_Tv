package com.arbelkilani.bingetv.data.source.local.base

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arbelkilani.bingetv.data.entities.episode.Episode
import com.arbelkilani.bingetv.data.entities.genre.Genre
import com.arbelkilani.bingetv.data.entities.season.SeasonData
import com.arbelkilani.bingetv.data.entities.tv.EpisodeToAir
import com.arbelkilani.bingetv.data.entities.tv.Network
import com.arbelkilani.bingetv.data.entities.tv.TvShowData
import com.arbelkilani.bingetv.data.source.local.Converters
import com.arbelkilani.bingetv.data.source.local.genre.GenreDao
import com.arbelkilani.bingetv.data.source.local.season.SeasonDao
import com.arbelkilani.bingetv.data.source.local.tv.TvDao

@Database(
    entities = [TvShowData::class, Genre::class, EpisodeToAir::class, SeasonData::class, Network::class, Episode::class],
    version = 15,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BingeTvDatabase : RoomDatabase() {
    abstract fun getGenreDao(): GenreDao
    abstract fun getTvDao(): TvDao
    abstract fun getSeasonDao(): SeasonDao
}