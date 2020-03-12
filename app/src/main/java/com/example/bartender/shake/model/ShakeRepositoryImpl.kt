package com.example.bartender.shake.model

import com.example.bartender.repository.RepositoryContract
import com.example.bartender.repository.database.CocktailDatabase
import com.example.bartender.repository.network.CocktailsClient
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ShakeRepositoryImpl @Inject constructor(private val client: CocktailsClient, private val database: CocktailDatabase) :
    RepositoryContract.Shake {

    override fun getIngredientsOnline(): Single<Ingredients> {

        return client
            .getIngredients()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun cacheIngredients(ingredients: Ingredients): Completable {
        return database
            .cocktailsDaoShake()
            .cacheIngredients(ingredients)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getIngredientsCache(): Single<Ingredients> {

        return database
            .cocktailsDaoShake()
            .getIngredientsCache()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    override fun getShakerCurrent(): Single<List<Ingredient>> {
        return database
            .cocktailsDaoShake()
            .getShakerCurrent()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun addIngredient(ingredient: Ingredient): Completable {
        return database
            .cocktailsDaoShake()
            .addIngredient(ingredient)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun removeIngredient(ingredient: Ingredient): Completable {
        return database
            .cocktailsDaoShake()
            .removeIngredient(ingredient)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}