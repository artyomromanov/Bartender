package com.example.bartender.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
<<<<<<< HEAD:app/src/main/java/com/example/bartender/search/viewmodel/SearchViewModel.kt
import com.example.bartender.search.model.Drink
import com.example.bartender.repository.RepositoryContract
import com.example.bartender.repository.database.SearchResult

class SearchViewModel(private val repository: RepositoryContract.Search) : ViewModel() {
=======
import com.example.bartender.Repository
import com.example.bartender.database.SearchResult
import com.example.bartender.model.Drink
import io.reactivex.disposables.CompositeDisposable

class CocktailsViewModel(private val repository: Repository, private val favouritesRepository: Repository.Favourites) : ViewModel() {
>>>>>>> develop:app/src/main/java/com/example/bartender/viewmodel/CocktailsViewModel.kt

    private val disposable = CompositeDisposable()

    private var searchData = MutableLiveData<List<Drink>>()
    private val errorData = MutableLiveData<String>()

    private val savedSearchSuggestions = MutableLiveData<List<String>>()

    private val databaseErrorData = MutableLiveData<String>()
    private val databaseDataSavedSuccess = MutableLiveData<Boolean>()

<<<<<<< HEAD:app/src/main/java/com/example/bartender/search/viewmodel/SearchViewModel.kt
=======
    private val favouritesData = MutableLiveData<List<Drink>>()
    private val favouritesDataError = MutableLiveData<String>()
    private val favouritesDataSavedSuccess = MutableLiveData<Boolean>()


>>>>>>> develop:app/src/main/java/com/example/bartender/viewmodel/CocktailsViewModel.kt

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

    fun saveCurrentSearchResult(result: SearchResult) {
        disposable.add(
            repository.saveLastSearchResult(result).subscribe({ databaseDataSavedSuccess.value = true }, { databaseErrorData.value = it.message })
        )
    }

    fun getFavourites(){

        disposable.add(
            favouritesRepository
                .getFavourites()
                .subscribe({data ->  favouritesData.value = data}, { error -> favouritesDataError.value = error.message })
        )
    }
    fun addFavourite(drink : Drink){

        disposable.add(
            favouritesRepository
                .addFavourite(drink)
                .subscribe ({ favouritesDataSavedSuccess.value = true},{favouritesDataError.value = it.message})
        )
    }

    fun getSearchResultsLiveData() = searchData as LiveData<List<Drink>>
    fun getErrorData() = errorData as LiveData<String>

    fun getSuggestionsLiveData() = savedSearchSuggestions as LiveData<List<String>>

    fun getDatabaseErrorData() = databaseErrorData as LiveData<String>
    fun getDatabaseDataSaved() = databaseDataSavedSuccess as LiveData<Boolean>

<<<<<<< HEAD:app/src/main/java/com/example/bartender/search/viewmodel/SearchViewModel.kt
    override fun onCleared() {
        disposable.clear()
    }
=======
    fun getFavouritesData() = favouritesData as LiveData<List<Drink>>
    fun getFavouritesDataError() = favouritesDataError as LiveData<String>
    fun getFavouritesDataSaveSuccess() = favouritesDataSavedSuccess as LiveData<Boolean>


    ///////////////////////

>>>>>>> develop:app/src/main/java/com/example/bartender/viewmodel/CocktailsViewModel.kt
}