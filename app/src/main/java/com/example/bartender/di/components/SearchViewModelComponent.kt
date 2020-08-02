package com.example.bartender.di.components

import com.example.bartender.HomeActivity
import com.example.bartender.di.modules.viewmodels.CocktailsViewModelModule
import com.example.bartender.di.scopes.ActivityScope
<<<<<<< HEAD:app/src/main/java/com/example/bartender/di/components/SearchViewModelComponent.kt
import com.example.bartender.search.view.SearchFragment
import dagger.Component

@ActivityScope
@Component(modules = [SearchViewModelModule::class, FavouritesViewModelModule::class], dependencies = [AppComponent::class])
interface SearchViewModelComponent {

    fun injectSearchFragment(fragment: SearchFragment)
=======
import dagger.Component

@ActivityScope
@Component(modules = [CocktailsViewModelModule::class], dependencies = [AppComponent::class])
interface ViewModelComponent {

    fun injectHomeActivity(activity : HomeActivity)
>>>>>>> develop:app/src/main/java/com/example/bartender/di/components/ViewModelComponent.kt

}