package com.example.bartender.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bartender.search.model.Drink
import com.example.bartender.search.model.Repository

class SearchViewModel(private val repository: Repository) : ViewModel() {

    private val disposable = io.reactivex.disposables.CompositeDisposable()

    private val searchData = MutableLiveData<List<Drink>>()
    private val errorData = MutableLiveData<String>()
    private val savedSearchData = MutableLiveData<List<Drink>>()
    private val databaseErrorData = MutableLiveData<String>()
    private val databaseDataSaved = MutableLiveData<Boolean>()

    fun getSearchResults(query: String) {
        disposable.add(
            repository.getSearchResults(query).subscribe({ data -> searchData.value = data }, { error -> errorData.value = error.message })
        )
    }

    fun getLastSavedSearch() {
        disposable.add(
            repository.getLastSearchResults().subscribe({ data -> savedSearchData.value = data }, { error -> databaseErrorData.value = error.message })
        )
    }

    fun saveCurrentSearchResults(list: List<Drink>) {
        disposable.add(
            repository.saveLastSearchResults(list).subscribe({ databaseDataSaved.value = true }, { databaseErrorData.value = it.message })
        )
    }

    fun getSearchData() = searchData as LiveData<List<Drink>>
    fun getErrorData() = errorData as LiveData<String>
    fun getSavedSearchData() = savedSearchData as LiveData<List<Drink>>
    fun getDatabaseErrorData() = databaseErrorData as LiveData<String>
    fun getDatabaseDataSaved() = databaseDataSaved as LiveData<Boolean>

}