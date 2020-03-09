package com.example.bartender.search.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bartender.search.model.Drink
import com.google.gson.annotations.SerializedName

@Entity(tableName = "search_results")
data class SearchResult(

    @PrimaryKey
    @SerializedName("list")
    val strDrink: List<Drink>

)