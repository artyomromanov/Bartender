package com.example.bartender.search.model

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val client: CocktailsClient) : Repository {

    override fun getSearchResults(query: String): Single<List<Drink>> {

        return client
            .getSearchResults(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.drinks }

    }

}