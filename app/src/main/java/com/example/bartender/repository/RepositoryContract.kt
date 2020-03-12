package com.example.bartender.repository

import com.example.bartender.favourites.model.RandomModel
import com.example.bartender.repository.database.SearchResult
import com.example.bartender.search.model.Drink
import com.example.bartender.shake.model.Ingredient
import com.example.bartender.shake.model.Ingredients
import io.reactivex.Completable
import io.reactivex.Single

interface RepositoryContract {

    interface Favourites {

        fun getRandomCocktail() : Single<RandomModel>

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

