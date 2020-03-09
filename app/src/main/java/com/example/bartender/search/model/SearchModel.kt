package com.example.bartender.search.model


import com.google.gson.annotations.SerializedName

data class SearchModel(
    @SerializedName("drinks")
    val drinks: List<Drink>
)