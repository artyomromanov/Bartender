package com.example.bartender.search.database

import androidx.room.TypeConverter
import com.example.bartender.search.model.Drink
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromString(value: String): List<Drink> {
        val listType: Type = object : TypeToken<List<Drink>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<Drink>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}