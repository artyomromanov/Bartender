package com.example.bartender.di.modules

import android.content.Context
<<<<<<< HEAD
import com.example.bartender.repository.database.CocktailDatabase
=======
import com.example.bartender.database.CocktailDatabase
>>>>>>> develop
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule(private val context : Context) {

    @Provides
    fun provideDatabase() : CocktailDatabase {

        return CocktailDatabase.getInstance(context)

    }

}