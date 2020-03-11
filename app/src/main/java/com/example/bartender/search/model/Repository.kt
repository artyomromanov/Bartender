package com.example.bartender.search.model

import com.example.bartender.search.database.SearchResult
import io.reactivex.Completable
import io.reactivex.Single

interface Repository {

    fun getSearchResults(query : String) : Single<List<Drink>>

    fun getSuggestions(query: String) : Single<List<String>>

    fun saveLastSearchResult(result: SearchResult) : Completable

}
