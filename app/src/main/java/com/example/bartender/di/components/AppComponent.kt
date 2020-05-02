package com.example.bartender.di.components

import com.example.bartender.util.MyApp
import com.example.bartender.database.CocktailDatabase
import com.example.bartender.di.modules.DatabaseModule
import com.example.bartender.di.modules.NetworkModule
import com.example.bartender.model.CocktailsClient
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, DatabaseModule::class])
@Singleton
interface AppComponent {

    fun inject(application: MyApp)

    fun database() : CocktailDatabase

    fun networkService() : CocktailsClient

}