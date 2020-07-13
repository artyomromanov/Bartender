package com.example.bartender.util

import com.example.bartender.model.Drink

const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
const val SEARCH_ENDPOINT = "search.php"
const val SMALL_IMAGE_ADDITION = "/preview"
val dummyDrink: Drink = Drink(
    "007",
    "Dummy Drink",
    null,
    "Dummy Category",
    "Certainly not",
    "Dummy Glass",
    "Not for drinkin",
    "https://www.thecocktaildb.com/images/media/drink/k1xatq1504389300.jpg",
    "Ing1",
    "Ing2",
    null,
    null,
    null,
    null
)