package com.example.bartender.database

import androidx.room.*
import com.example.bartender.search.model.Drink
import com.example.bartender.shake.model.Ingredient
import com.example.bartender.shake.model.Ingredients
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

    @Dao
    interface Shake {
        //Entity Ingredient
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun cacheIngredients(ingredientList : Ingredients) : Completable

        @Query("SELECT * FROM ingredients_cache")
        fun getIngredientsCache() : Single<Ingredients>

        //Entity Ingredients!!!
        @Query("SELECT * FROM ingredients_table")
        fun getShakerCurrent(): Single<List<Ingredient>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun addIngredient(ingredient: Ingredient): Completable

        @Delete
        fun removeIngredient(ingredient: Ingredient): Completable

    }
}