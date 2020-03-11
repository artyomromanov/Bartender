package com.example.bartender.search.database

import androidx.room.*
import com.example.bartender.search.model.Drink
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CocktailsDao {

    @Query("SELECT * FROM favourites_table")
    fun getFavourites(): Single<List<Drink>>

    @Query("SELECT search_query FROM search_results WHERE search_query LIKE '%' || :query  || '%'")
    fun getSuggestions(query: String): Single<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavourite(list : List<Drink>): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addSearchResult(result: SearchResult): Completable

}