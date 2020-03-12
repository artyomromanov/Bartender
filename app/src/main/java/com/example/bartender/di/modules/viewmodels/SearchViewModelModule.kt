package com.example.bartender.di.modules.viewmodels

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bartender.repository.RepositoryContract
import com.example.bartender.repository.database.CocktailDatabase
import com.example.bartender.di.scopes.ActivityScope
import com.example.bartender.repository.network.CocktailsClient
import com.example.bartender.search.model.RepositorySearchImpl
import com.example.bartender.search.viewmodel.SearchViewModel
import com.example.bartender.search.viewmodel.SearchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class SearchViewModelModule(private val fragment: Fragment) {

    @ActivityScope
    @Provides
    fun provideRepository(client : CocktailsClient, database: CocktailDatabase) : RepositoryContract.Search {
        return RepositorySearchImpl(client, database)
    }
    @ActivityScope
    @Provides
    fun provideSearchViewModelFactory(repositoryContract: RepositoryContract.Search) : SearchViewModelFactory {
        return SearchViewModelFactory(repositoryContract)
    }
    @ActivityScope
    @Provides
    fun provideSearchViewModel(factory : SearchViewModelFactory) : SearchViewModel {
        return ViewModelProvider(fragment, factory).get(SearchViewModel::class.java)
    }
}
