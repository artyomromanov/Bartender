package com.example.bartender.di.modules.viewmodels

import androidx.lifecycle.ViewModelProvider
import com.example.bartender.HomeActivity
import com.example.bartender.Repository
import com.example.bartender.database.CocktailDatabase
import com.example.bartender.di.scopes.ActivityScope
import com.example.bartender.favourites_fragment.RepositoryFavouritesImpl
import com.example.bartender.model.CocktailsClient
import com.example.bartender.model.RepositoryImpl
import com.example.bartender.viewmodel.CocktailsViewModel
import com.example.bartender.viewmodel.CocktailsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class CocktailsViewModelModule(private val activity: HomeActivity) {

    @ActivityScope
    @Provides
    fun provideRepository(client : CocktailsClient, database: CocktailDatabase) : Repository {
        return RepositoryImpl(client, database)
    }
    @ActivityScope
    @Provides
    fun provideRepositoryFavourites(database: CocktailDatabase) : Repository.Favourites {
        return RepositoryFavouritesImpl(database)
    }
    @ActivityScope
    @Provides
    fun provideSearchViewModelFactory(repository: Repository, favouritesRepository: Repository.Favourites) : CocktailsViewModelFactory {
        return CocktailsViewModelFactory(repository, favouritesRepository)
    }

    @ActivityScope
    @Provides
    fun provideSearchViewModel(factoryCocktails : CocktailsViewModelFactory) : CocktailsViewModel {
        return ViewModelProvider(activity, factoryCocktails).get(CocktailsViewModel::class.java)
    }
}
