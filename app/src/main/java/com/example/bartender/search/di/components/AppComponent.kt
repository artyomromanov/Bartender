package com.example.bartender.search.di.components

import com.example.bartender.MyApp
import com.example.bartender.search.database.CocktailDatabase
import com.example.bartender.search.di.modules.DatabaseModule
import com.example.bartender.search.di.modules.NetworkModule
import com.example.bartender.search.model.CocktailsClient
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, DatabaseModule::class])
@Singleton
interface AppComponent {

    fun inject(application: MyApp)

    fun database() : CocktailDatabase

    fun networkService() : CocktailsClient

}