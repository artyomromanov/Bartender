package com.example.bartender.di.components

import com.example.bartender.HomeActivity
import com.example.bartender.di.modules.viewmodels.CocktailsViewModelModule
import com.example.bartender.di.scopes.ActivityScope
import dagger.Component

@ActivityScope
@Component(modules = [CocktailsViewModelModule::class], dependencies = [AppComponent::class])
interface ViewModelComponent {

    fun injectHomeActivity(activity : HomeActivity)

}