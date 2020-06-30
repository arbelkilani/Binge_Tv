package com.arbelkilani.bingetv.data.source.local

import androidx.room.TypeConverter
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
    }


}