package com.example.bartender

import com.example.bartender.search.model.Drink
import com.example.bartender.shake.model.Ingredient
import io.reactivex.Completable
import io.reactivex.Single

interface Repository {

    interface Favourites {

        fun getFavourites() : Single<List<Drink>>

        fun addFavourite(drink : Drink) : Completable

    }
    interface Shake {

        fun getIngredients() : Single<List<Ingredient>>

    }
    interface Search {

        fun getSearchResults(query: String): Single<List<Drink>>

        fun getSuggestions(query: String): Single<List<String>>

        fun saveLastSearchResult(result: com.example.bartender.database.SearchResult): Completable
    }

}

