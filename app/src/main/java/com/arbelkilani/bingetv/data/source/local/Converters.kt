package com.arbelkilani.bingetv.data.source.local

import androidx.room.TypeConverter
import com.arbelkilani.bingetv.data.entities.tv.maze.details.NextEpisodeData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromNextEpisode(item: NextEpisodeData?): String {
            if (item == null)
                return ""
            return Gson().toJson(item)
        }

        @TypeConverter
        @JvmStatic
        fun toNextEpisode(value: String?): NextEpisodeData? {
            if (value == null)
                return null
            val type: Type = object : TypeToken<NextEpisodeData>() {}.type
            return Gson().fromJson(value, type)
        }

    }


}