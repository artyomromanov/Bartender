package com.example.bartender.di.modules.viewmodels

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bartender.repository.RepositoryContract
import com.example.bartender.repository.database.CocktailDatabase
import com.example.bartender.di.scopes.ActivityScope
import com.example.bartender.repository.network.CocktailsClient
import com.example.bartender.shake.model.ShakeRepositoryImpl
import com.example.bartender.shake.viewmodel.ShakeViewModel
import com.example.bartender.shake.viewmodel.ShakeViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ShakeViewModelModule(private val fragment: Fragment) {

    @ActivityScope
    @Provides
    fun provideRepository(client : CocktailsClient, database: CocktailDatabase) : RepositoryContract.Shake {
        return ShakeRepositoryImpl(client, database)
    }
    @ActivityScope
    @Provides
    fun provideShakeViewModelFactory(repositoryContract: RepositoryContract.Shake) : ShakeViewModelFactory {
        return ShakeViewModelFactory(repositoryContract)
    }
    @ActivityScope
    @Provides
    fun provideShakeViewModel(factory : ShakeViewModelFactory) : ShakeViewModel {
        return ViewModelProvider(fragment, factory).get(ShakeViewModel::class.java)
    }
}
