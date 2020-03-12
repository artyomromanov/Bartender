package com.example.bartender.di.modules.viewmodels

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bartender.Repository
import com.example.bartender.database.CocktailDatabase
import com.example.bartender.di.scopes.ActivityScope
import com.example.bartender.search.model.CocktailsClient
import com.example.bartender.shake.model.ShakeRepositoryImpl
import com.example.bartender.shake.viewmodel.ShakeViewModel
import com.example.bartender.shake.viewmodel.ShakeViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ShakeViewModelModule(private val fragment: Fragment) {

    @ActivityScope
    @Provides
    fun provideRepository(client : CocktailsClient, database: CocktailDatabase) : Repository.Shake {
        return ShakeRepositoryImpl(client, database)
    }
    @ActivityScope
    @Provides
    fun provideShakeViewModelFactory(repository: Repository.Shake) : ShakeViewModelFactory {
        return ShakeViewModelFactory(repository)
    }
    @ActivityScope
    @Provides
    fun provideShakeViewModel(factory : ShakeViewModelFactory) : ShakeViewModel {
        return ViewModelProvider(fragment, factory).get(ShakeViewModel::class.java)
    }
}
