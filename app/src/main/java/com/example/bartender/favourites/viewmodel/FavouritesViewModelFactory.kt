package com.example.bartender.favourites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bartender.Repository
import javax.inject.Inject


class FavouritesViewModelFactory @Inject constructor(private val repository: Repository.Favourites) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavouritesViewModel(repository) as T
    }
}