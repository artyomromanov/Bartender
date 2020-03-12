package com.example.bartender.shake.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ingredients_cache")
data class Ingredients(
    @PrimaryKey
    @SerializedName("drinks")
    val drinks: List<Ingredient>
)