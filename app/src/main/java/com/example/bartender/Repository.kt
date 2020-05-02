package com.example.bartender

import com.example.bartender.database.SearchResult
import com.example.bartender.model.Drink
import io.reactivex.Completable
import io.reactivex.Single

interface Repository {

    interface Favourites {

        fun getFavourites() : Single<List<Drink>>

        fun addFavourite(drink : Drink) : Completable

    }

    fun getSearchResults(query : String) : Single<List<Drink>>

    fun getSuggestions(query: String) : Single<List<String>>

    fun saveLastSearchResult(result: SearchResult) : Completable

}

