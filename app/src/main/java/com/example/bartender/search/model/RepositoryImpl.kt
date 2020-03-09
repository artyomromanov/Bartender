package com.example.bartender.search.model

import com.example.bartender.search.database.CocktailDatabase
import com.example.bartender.search.database.SearchResult
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val client: CocktailsClient, private val database: CocktailDatabase) : Repository {

    override fun getSearchResults(query: String): Single<List<Drink>> {

        return client
            .getSearchResults(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.drinks }

    }

    override fun getLastSearchResults(): Single<List<Drink>> {

        return database
            .cocktailsDao()
            .getLastSearchResults()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    override fun saveLastSearchResults(list: List<Drink>): Completable {
        return database
            .cocktailsDao()
            .addSearchResult(SearchResult(list))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}