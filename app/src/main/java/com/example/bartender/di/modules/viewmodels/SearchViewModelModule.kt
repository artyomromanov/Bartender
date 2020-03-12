package com.example.bartender.di.modules.viewmodels

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bartender.Repository
import com.example.bartender.database.CocktailDatabase
import com.example.bartender.di.scopes.ActivityScope
import com.example.bartender.search.model.CocktailsClient
import com.example.bartender.search.model.RepositoryImpl
import com.example.bartender.search.viewmodel.SearchViewModel
import com.example.bartender.search.viewmodel.SearchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class SearchViewModelModule(private val fragment: Fragment) {

    @ActivityScope
    @Provides
    fun provideRepository(client : CocktailsClient, database: CocktailDatabase) : Repository.Search {
        return RepositoryImpl(client, database)
    }
    @ActivityScope
    @Provides
    fun provideSearchViewModelFactory(repository: Repository.Search) : SearchViewModelFactory {
        return SearchViewModelFactory(repository)
    }
    @ActivityScope
    @Provides
    fun provideSearchViewModel(factory : SearchViewModelFactory) : SearchViewModel {
        return ViewModelProvider(fragment, factory).get(SearchViewModel::class.java)
    }
}
