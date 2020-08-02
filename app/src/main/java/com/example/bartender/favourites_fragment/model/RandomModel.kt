package com.example.bartender.favourites.model


import com.google.gson.annotations.SerializedName

data class RandomModel(
    @SerializedName("drinks")
    val drinksList: List<RandomDrink>
)