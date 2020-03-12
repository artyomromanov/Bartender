package com.example.bartender.di.modules.viewmodels

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bartender.repository.RepositoryContract
import com.example.bartender.repository.database.CocktailDatabase
import com.example.bartender.di.scopes.ActivityScope
import com.example.bartender.favourites.model.RepositoryFavouritesImpl
import com.example.bartender.favourites.viewmodel.FavouritesViewModel
import com.example.bartender.favourites.viewmodel.FavouritesViewModelFactory
import com.example.bartender.repository.network.CocktailsClient
import dagger.Module
import dagger.Provides

@Module
class FavouritesViewModelModule(private val fragment: Fragment) {

    @ActivityScope
    @Provides
    fun provideRepositoryFavourites(client: CocktailsClient, database: CocktailDatabase) : RepositoryContract.Favourites {
        return RepositoryFavouritesImpl(client, database)
    }
    @ActivityScope
    @Provides
    fun provideFavouritesViewModelFactory(repositoryContract: RepositoryContract.Favourites) : FavouritesViewModelFactory {
        return FavouritesViewModelFactory(repositoryContract)
    }
    @ActivityScope
    @Provides
    fun provideFavouritesViewModel(factory : FavouritesViewModelFactory) : FavouritesViewModel {
        return ViewModelProvider(fragment, factory).get(FavouritesViewModel::class.java)
    }
}
