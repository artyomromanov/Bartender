package com.example.bartender.di.components

import com.example.bartender.di.modules.viewmodels.FavouritesViewModelModule
import com.example.bartender.di.modules.viewmodels.SearchViewModelModule
import com.example.bartender.di.scopes.ActivityScope
import com.example.bartender.search.view.SearchFragment
import dagger.Component

@ActivityScope
@Component(modules = [SearchViewModelModule::class, FavouritesViewModelModule::class], dependencies = [AppComponent::class])
interface SearchViewModelComponent {

    fun injectSearchFragment(fragment: SearchFragment)

}