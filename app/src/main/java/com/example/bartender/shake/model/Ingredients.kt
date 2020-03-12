package com.example.bartender.shake.model


import com.google.gson.annotations.SerializedName

data class Ingredients(
    @SerializedName("drinks")
    val drinks: List<Ingredient>
)