package com.example.bartender.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bartender.Repository
import javax.inject.Inject

class SearchViewModelFactory @Inject constructor(private val repository: Repository.Search) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

       return SearchViewModel(repository) as T

    }
}