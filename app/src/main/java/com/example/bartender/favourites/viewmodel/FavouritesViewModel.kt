package com.example.bartender.favourites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bartender.Repository
import com.example.bartender.search.model.Drink
import io.reactivex.disposables.CompositeDisposable

class FavouritesViewModel(private val repository: Repository.Favourites) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val favouritesData = MutableLiveData<List<Drink>>()
    private val favouritesDataError = MutableLiveData<String>()
    private val favouritesDataSavedSuccess = MutableLiveData<Boolean>()

    fun getFavourites(){

        disposable.add(
            repository
                .getFavourites()
                .subscribe({data ->  favouritesData.value = data}, { error -> favouritesDataError.value = error.message })
        )
    }
    fun addFavourite(drink : Drink){

        disposable.add(
            repository
                .addFavourite(drink)
                .subscribe ({ favouritesDataSavedSuccess.value = true},{favouritesDataError.value = it.message})
        )
    }


    fun getFavouritesData() = favouritesData as LiveData<List<Drink>>
    fun getFavouritesDataError() = favouritesDataError as LiveData<String>
    fun getFavouritesDataSaveSuccess() = favouritesDataSavedSuccess as LiveData<Boolean>


}