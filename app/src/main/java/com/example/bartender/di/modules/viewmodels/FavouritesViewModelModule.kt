package com.example.bartender.di.modules.viewmodels

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bartender.Repository
import com.example.bartender.di.scopes.ActivityScope
import com.example.bartender.favourites.RepositoryFavouritesImpl
import com.example.bartender.favourites.viewmodel.FavouritesViewModel
import com.example.bartender.favourites.viewmodel.FavouritesViewModelFactory
import com.example.bartender.search.database.CocktailDatabase
import dagger.Module
import dagger.Provides

@Module
class FavouritesViewModelModule(private val fragment: Fragment) {

    @ActivityScope
    @Provides
    fun provideRepositoryFavourites(database: CocktailDatabase) : Repository.Favourites {
        return RepositoryFavouritesImpl(database)
    }
    @ActivityScope
    @Provides
    fun provideFavouritesViewModelFactory(repository: Repository.Favourites) : FavouritesViewModelFactory {
        return FavouritesViewModelFactory(repository)
    }
    @ActivityScope
    @Provides
    fun provideFavouritesViewModel(factory : FavouritesViewModelFactory) : FavouritesViewModel {
        return ViewModelProvider(fragment, factory).get(FavouritesViewModel::class.java)
    }
}
