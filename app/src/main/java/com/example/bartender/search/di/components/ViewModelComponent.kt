package com.example.bartender.search.di.components

import com.example.bartender.favourites.FavouritesFragment
import com.example.bartender.search.di.modules.ViewModelModule
import com.example.bartender.search.di.scopes.ActivityScope
import com.example.bartender.search.view.SearchFragment
import dagger.Component

@ActivityScope
@Component(modules = [ViewModelModule::class], dependencies = [AppComponent::class])
interface ViewModelComponent {

    fun injectSearchFragment(fragment: SearchFragment)

    fun injectFavouritesFragment(fragment : FavouritesFragment)

}