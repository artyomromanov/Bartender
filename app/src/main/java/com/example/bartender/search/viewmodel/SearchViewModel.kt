package com.example.bartender.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bartender.search.database.SearchResult
import com.example.bartender.search.model.Drink
import com.example.bartender.search.model.Repository

class SearchViewModel(private val repository: Repository) : ViewModel() {

    private val disposable = io.reactivex.disposables.CompositeDisposable()

    private val searchData = MutableLiveData<List<Drink>>()
    private val errorData = MutableLiveData<String>()

    private val savedSearchSuggestions = MutableLiveData<List<String>>()

    private val databaseErrorData = MutableLiveData<String>()
    private val databaseDataSavedSuccess = MutableLiveData<Boolean>()

    fun getSearchResults(query: String) {
        disposable.add(
            repository.getSearchResults(query).subscribe({ data -> searchData.value = data }, { error -> errorData.value = error.message })
        )
    }

    fun getSuggestions(query: String = "") {
        disposable.add(
            repository.getSuggestions(query).subscribe({ data -> savedSearchSuggestions.value = data }, { error -> databaseErrorData.value = error.message })
        )
    }

    fun saveCurrentSearchResults(result: SearchResult) {
        disposable.add(
            repository.saveLastSearchResult(result).subscribe({ databaseDataSavedSuccess.value = true }, { databaseErrorData.value = it.message })
        )
    }

    fun getSearchResultsLiveData() = searchData as LiveData<List<Drink>>
    fun getErrorData() = errorData as LiveData<String>

    fun getSuggestionsLiveData() = savedSearchSuggestions as LiveData<List<String>>

    fun getDatabaseErrorData() = databaseErrorData as LiveData<String>
    fun getDatabaseDataSaved() = databaseDataSavedSuccess as LiveData<Boolean>

}