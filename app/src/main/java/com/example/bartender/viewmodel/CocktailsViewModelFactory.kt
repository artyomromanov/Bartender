package com.example.bartender.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bartender.Repository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class CocktailsViewModelFactory @Inject constructor(private val repository: Repository, private val repositoryFavourites : Repository.Favourites) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

       return CocktailsViewModel(repository, repositoryFavourites) as T

    }
}