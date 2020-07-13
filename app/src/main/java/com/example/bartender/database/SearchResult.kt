package com.example.bartender.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bartender.model.Drink
import com.google.gson.annotations.SerializedName

@Entity(tableName = "search_results")
data class SearchResult(

    @PrimaryKey
    @SerializedName("search_query")
    @ColumnInfo(name = "search_query")
    val strQuery: String,

    @SerializedName("list")
    val strDrink: List<Drink>


)