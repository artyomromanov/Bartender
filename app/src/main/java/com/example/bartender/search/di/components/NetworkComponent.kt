package com.example.bartender.search.di.components

import com.example.bartender.search.di.modules.NetworkModule
import com.example.bartender.search.model.CocktailsClient
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface NetworkComponent {

    fun client() : CocktailsClient

}