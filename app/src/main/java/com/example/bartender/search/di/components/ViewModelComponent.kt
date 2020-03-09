package com.example.bartender.search.di.components

import androidx.fragment.app.Fragment
import com.example.bartender.HomeActivity
import com.example.bartender.search.di.modules.ViewModelModule
import com.example.bartender.search.di.scopes.ActivityScope
import com.example.bartender.search.view.SearchFragment
import dagger.Component

@ActivityScope
@Component(modules = [ViewModelModule::class], dependencies = [AppComponent::class])
interface ViewModelComponent {

    fun injectActivity(fragment: SearchFragment)

}