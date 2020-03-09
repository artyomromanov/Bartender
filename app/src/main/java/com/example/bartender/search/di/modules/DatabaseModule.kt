package com.example.bartender.search.di.modules

import android.content.Context
import com.example.bartender.search.database.CocktailDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule(private val context : Context) {

    @Provides
    fun provideDatabase() : CocktailDatabase{

        return CocktailDatabase.getInstance(context)

    }

}