package com.example.bartender.search.database

import androidx.room.*
import com.example.bartender.search.model.Drink
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CocktailsDao {

    @Query("SELECT * FROM favourites_table")
    fun getFavourites(): Single<List<Drink>>

    @Query("SELECT * FROM favourites_table")
    fun getLastSearchResults(): Single<List<Drink>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addResult(list : List<Drink>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSearchResult(result: SearchResult): Completable

}