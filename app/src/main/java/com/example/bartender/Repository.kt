package com.example.bartender

import com.example.bartender.database.SearchResult
import com.example.bartender.search.model.Drink
import com.example.bartender.shake.model.Ingredient
import com.example.bartender.shake.model.Ingredients
import io.reactivex.Completable
import io.reactivex.Single

interface Repository {

    interface Favourites {

        fun getFavourites() : Single<List<Drink>>

        fun addFavourite(drink : Drink) : Completable

        fun removeFavourite(drink : Drink) : Completable

    }
    interface Shake {

        fun getIngredientsOnline() : Single<Ingredients>

        fun cacheIngredients(ingredients: Ingredients) : Completable

        fun getIngredientsCache() : Single<Ingredients>

        fun getShakerCurrent(): Single<List<Ingredient>>

        fun addIngredient(ingredient: Ingredient): Completable

        fun removeIngredient(ingredient: Ingredient): Completable

    }
    interface Search {

        fun getSearchResults(query: String): Single<List<Drink>>

        fun getSuggestions(query: String): Single<List<String>>

        fun saveLastSearchResult(result: SearchResult): Completable
    }

}

