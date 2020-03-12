package com.example.bartender.di.components

import com.example.bartender.di.modules.viewmodels.FavouritesViewModelModule
import com.example.bartender.di.scopes.ActivityScope
import com.example.bartender.favourites.view.FavouritesFragment
import dagger.Component

@ActivityScope
@Component(modules = [FavouritesViewModelModule::class], dependencies = [AppComponent::class])
interface FavouritesViewModelComponent {

    fun injectFavouritesFragment(fragment: FavouritesFragment)

}