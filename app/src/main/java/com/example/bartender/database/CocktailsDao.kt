package com.example.bartender.database

import androidx.room.*
import com.example.bartender.search.model.Drink
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CocktailsDao {

    @Dao
    interface Search {

        @Query("SELECT search_query FROM search_results WHERE search_query LIKE '%' || :query  || '%'")
        fun getSuggestions(query: String): Single<List<String>>

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        fun addSearchResult(result: SearchResult): Completable

    }
    @Dao
    interface Favourites {

        @Query("SELECT * FROM favourites_table")
        fun getFavourites(): Single<List<Drink>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun addFavourite(drink: Drink): Completable

        @Delete
        fun removeFavourite(drink: Drink): Completable

    }
}