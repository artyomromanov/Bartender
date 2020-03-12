package com.example.bartender.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bartender.repository.RepositoryContract
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory @Inject constructor(private val repository: RepositoryContract.Search) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

       return SearchViewModel(repository) as T

    }
}