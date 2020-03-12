package com.example.bartender.favourites

import com.example.bartender.search.model.Drink
import com.example.bartender.Repository
import com.example.bartender.database.CocktailDatabase
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RepositoryFavouritesImpl @Inject constructor(private val database: CocktailDatabase) : Repository.Favourites {

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
}