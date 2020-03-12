package com.example.bartender.shake.model

import com.example.bartender.Repository
import com.example.bartender.database.CocktailDatabase
import com.example.bartender.search.model.CocktailsClient
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ShakeRepositoryImpl @Inject constructor(private val client: CocktailsClient, private val database: CocktailDatabase) :
    Repository.Shake {

    override fun getIngredients(): Single<List<Ingredient>> {

        return client
            .getIngredients()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.drinks }

    }

}