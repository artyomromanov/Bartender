package com.example.bartender.search.di.modules

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bartender.HomeActivity
import com.example.bartender.search.di.scopes.ActivityScope
import com.example.bartender.search.model.CocktailsClient
import com.example.bartender.search.model.Repository
import com.example.bartender.search.model.RepositoryImpl
import com.example.bartender.search.view.SearchFragment
import com.example.bartender.search.viewmodel.SearchViewModel
import com.example.bartender.search.viewmodel.SearchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule(private val fragment: SearchFragment) {

    @ActivityScope
    @Provides
    fun provideRepository(client : CocktailsClient) : Repository {

        return RepositoryImpl(client)

    }

    @ActivityScope
    @Provides
    fun provideViewModelFactory(repository: Repository) : SearchViewModelFactory {

        return SearchViewModelFactory(repository)

    }
    @ActivityScope
    @Provides
    fun provideViewModel(factory : SearchViewModelFactory) : SearchViewModel {

        return ViewModelProvider(fragment, factory).get(SearchViewModel::class.java)

    }

}
