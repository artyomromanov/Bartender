package com.example.bartender.shake.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ingredients_table")
data class Ingredient(
    @PrimaryKey
    @SerializedName("strIngredient1")
    val strIngredient1: String
)