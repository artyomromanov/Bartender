package com.example.bartender.di.modules

import android.content.Context
import com.example.bartender.repository.database.CocktailDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule(private val context : Context) {

    @Provides
    fun provideDatabase() : CocktailDatabase {

        return CocktailDatabase.getInstance(context)

    }

}