package com.arbelkilani.bingetv.data.source.local

import androidx.room.TypeConverter
import com.arbelkilani.bingetv.data.entities.episode.Episode
import com.arbelkilani.bingetv.data.entities.genre.Genre
import com.arbelkilani.bingetv.data.entities.season.Season
import com.arbelkilani.bingetv.data.entities.tv.EpisodeToAir
import com.arbelkilani.bingetv.data.entities.tv.Network
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromListInteger(list: List<Int>): String {
            return Gson().toJson(list)
        }

        @TypeConverter
        @JvmStatic
        fun toListString(value: String?): List<Int> {
            val listType: Type = object : TypeToken<List<Int>>() {}.type
            return Gson().fromJson(value, listType)
        }

        @TypeConverter
        @JvmStatic
        fun fromListGenres(list: List<Genre>): String {
            return Gson().toJson(list)
        }

        @TypeConverter
        @JvmStatic
        fun toListGenres(value: String?): List<Genre> {
            val listType: Type = object : TypeToken<List<Genre>>() {}.type
            return Gson().fromJson(value, listType)
        }

        @TypeConverter
        @JvmStatic
        fun fromListNetworks(list: List<Network>): String {
            return Gson().toJson(list)
        }

        @TypeConverter
        @JvmStatic
        fun toListNetworks(value: String?): List<Network> {
            val listType: Type = object : TypeToken<List<Network>>() {}.type
            return Gson().fromJson(value, listType)
        }

        @TypeConverter
        @JvmStatic
        fun fromListSeasons(list: List<Season>): String {
            return Gson().toJson(list)
        }

        @TypeConverter
        @JvmStatic
        fun toListSeasons(value: String?): List<Season> {
            val listType: Type = object : TypeToken<List<Season>>() {}.type
            return Gson().fromJson(value, listType)
        }

        @TypeConverter
        @JvmStatic
        fun fromEpisodeToAir(item: EpisodeToAir): String {
            return Gson().toJson(item)
        }

        @TypeConverter
        @JvmStatic
        fun toEpisodeToAir(value: String?): EpisodeToAir {
            val type: Type = object : TypeToken<EpisodeToAir>() {}.type
            return Gson().fromJson(value, type)
        }

        @TypeConverter
        @JvmStatic
        fun fromListEpisodes(list: List<Episode>): String {
            return Gson().toJson(list)
        }

        @TypeConverter
        @JvmStatic
        fun toListEpisode(value: String?): List<Episode> {
            val listType: Type = object : TypeToken<List<Episode>>() {}.type
            return Gson().fromJson(value, listType)
        }

    }


}