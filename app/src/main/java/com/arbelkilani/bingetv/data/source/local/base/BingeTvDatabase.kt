package com.arbelkilani.bingetv.data.source.local.base

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arbelkilani.bingetv.data.model.episode.Episode
import com.arbelkilani.bingetv.data.model.genre.Genre
import com.arbelkilani.bingetv.data.model.season.Season
import com.arbelkilani.bingetv.data.model.tv.EpisodeToAir
import com.arbelkilani.bingetv.data.model.tv.Network
import com.arbelkilani.bingetv.data.model.tv.TvShow
import com.arbelkilani.bingetv.data.source.local.Converters
import com.arbelkilani.bingetv.data.source.local.genre.GenreDao
import com.arbelkilani.bingetv.data.source.local.season.SeasonDao
import com.arbelkilani.bingetv.data.source.local.tv.TvDao

@Database(
    entities = [TvShow::class, Genre::class, EpisodeToAir::class, Season::class, Network::class, Episode::class],
    version = 12,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BingeTvDatabase : RoomDatabase() {
    abstract fun getGenreDao(): GenreDao
    abstract fun getTvDao(): TvDao
    abstract fun getSeasonDao(): SeasonDao
}