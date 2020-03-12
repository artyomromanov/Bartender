package com.example.bartender.favourites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bartender.repository.RepositoryContract
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
class FavouritesViewModelFactory @Inject constructor(private val repository: RepositoryContract.Favourites) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavouritesViewModel(repository) as T
    }
}