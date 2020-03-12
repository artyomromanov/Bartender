package com.example.bartender.search.model

import com.example.bartender.repository.RepositoryContract
import com.example.bartender.repository.database.CocktailDatabase
import com.example.bartender.repository.database.SearchResult
import com.example.bartender.repository.network.CocktailsClient
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RepositorySearchImpl @Inject constructor(private val client: CocktailsClient, private val database: CocktailDatabase) :
    RepositoryContract.Search {

    override fun getSearchResults(query: String): Single<List<Drink>> {

        return client
            .getSearchResults(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.drinks }

    }

    override fun getSuggestions(query: String): Single<List<String>> {

        return database
            .cocktailsDaoSearch()
            .getSuggestions(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    override fun saveLastSearchResult(result: SearchResult): Completable {
        return database
            .cocktailsDaoSearch()
            .addSearchResult(result)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}