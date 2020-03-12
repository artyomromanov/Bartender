package com.example.bartender

import com.example.bartender.search.model.Drink

const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
const val SEARCH_ENDPOINT = "search.php"
const val SMALL_IMAGE_ADDITION = "/preview"
const val INGREDIENTS_ENDPOINT = "list.php?i=list"
const val INGREDIENT_IMAGE_ENDPOINT = "https://www.thecocktaildb.com/images/ingredients/"
const val SMALL_IMAGE_INGREDIENT = "-Small.png"

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