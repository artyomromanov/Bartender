package com.example.bartender.search.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bartender.search.model.Drink

@TypeConverters(Converters::class)
@Database(entities = [Drink::class/*, FavouriteDrinks::class*/, SearchResult::class], version = 4, exportSchema = false)
abstract class CocktailDatabase : RoomDatabase() {

    abstract fun cocktailsDao(): CocktailsDao

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