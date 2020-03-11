package com.example.bartender.di.components

import com.example.bartender.di.modules.viewmodels.FavouritesViewModelModule
import com.example.bartender.di.modules.viewmodels.SearchViewModelModule
import com.example.bartender.di.scopes.ActivityScope
import com.example.bartender.favourites.view.FavouritesFragment
import com.example.bartender.search.view.SearchFragment
import dagger.Component

@ActivityScope
@Component(modules = [SearchViewModelModule::class, FavouritesViewModelModule::class], dependencies = [AppComponent::class])
interface ViewModelComponent {

    fun injectSearchFragment(fragment: SearchFragment)

    fun injectFavouritesFragment(fragment : FavouritesFragment)

}