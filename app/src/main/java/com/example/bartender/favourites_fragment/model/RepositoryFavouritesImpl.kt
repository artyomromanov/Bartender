package com.example.bartender.favourites.model

import com.example.bartender.search.model.Drink
import com.example.bartender.repository.RepositoryContract
import com.example.bartender.repository.database.CocktailDatabase
import com.example.bartender.repository.network.CocktailsClient
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RepositoryFavouritesImpl @Inject constructor(private val client: CocktailsClient, private val database: CocktailDatabase) : RepositoryContract.Favourites {


    override fun getRandomCocktail(): Single<RandomModel> {
        return client
            .getRandom()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    override fun getFavourites(): Single<List<Drink>> {
        return database
            .cocktailsDaoFavourites()
            .getFavourites()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun addFavourite(drink : Drink): Completable {
        return database
            .cocktailsDaoFavourites()
            .addFavourite(drink)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun removeFavourite(drink: Drink): Completable {
        return database
            .cocktailsDaoFavourites()
            .removeFavourite(drink)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}