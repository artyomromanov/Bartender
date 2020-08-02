<<<<<<< HEAD:app/src/main/java/com/example/bartender/repository/database/CocktailDatabase.kt
package com.example.bartender.repository.database
=======
package com.example.bartender.database
>>>>>>> develop:app/src/main/java/com/example/bartender/database/CocktailDatabase.kt

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
<<<<<<< HEAD:app/src/main/java/com/example/bartender/repository/database/CocktailDatabase.kt
import com.example.bartender.search.model.Drink
import com.example.bartender.shake.model.Ingredient
import com.example.bartender.shake.model.Ingredients
=======
import com.example.bartender.model.Drink
>>>>>>> develop:app/src/main/java/com/example/bartender/database/CocktailDatabase.kt

@TypeConverters(Converters::class)
@Database(entities = [Drink::class, SearchResult::class, Ingredient::class, Ingredients::class], version = 7, exportSchema = false)
abstract class CocktailDatabase : RoomDatabase() {

    abstract fun cocktailsDaoSearch(): CocktailsDao.Search
    abstract fun cocktailsDaoFavourites(): CocktailsDao.Favourites
    abstract fun cocktailsDaoShake(): CocktailsDao.Shake

    companion object {

        @Volatile
        private var INSTANCE : CocktailDatabase? = null

        fun getInstance(context: Context): CocktailDatabase {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {

                    instance = Room.databaseBuilder(

                        context.applicationContext,
                        CocktailDatabase::class.java,"Cocktail Database")
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance

                }

                return instance
            }
        }
    }
}