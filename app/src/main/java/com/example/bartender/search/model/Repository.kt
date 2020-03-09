package com.example.bartender.search.model

import io.reactivex.Completable
import io.reactivex.Single

interface Repository {

    fun getSearchResults(query : String) : Single<List<Drink>>

    fun getLastSearchResults() : Single<List<Drink>>

    fun saveLastSearchResults(list : List<Drink>) : Completable

}
