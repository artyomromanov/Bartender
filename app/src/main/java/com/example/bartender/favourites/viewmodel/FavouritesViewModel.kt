package com.example.bartender.favourites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bartender.favourites.model.RandomDrink
import com.example.bartender.favourites.model.RandomModel
import com.example.bartender.repository.RepositoryContract
import com.example.bartender.search.model.Drink
import io.reactivex.disposables.CompositeDisposable

class FavouritesViewModel(private val repository: RepositoryContract.Favourites) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val randomCocktailData = MutableLiveData<RandomDrink>()
    private val randomCocktailDataError = MutableLiveData<String>()

    private val favouritesData = MutableLiveData<List<Drink>>()
    private val favouritesDataError = MutableLiveData<String>()
    private val favouritesDataSavedSuccess = MutableLiveData<Boolean>()

    fun getRandomCocktail(){

        disposable.add(
            repository.getRandomCocktail()
                .map { it.drinksList[0] }
                .subscribe({data -> randomCocktailData.value = data} , {error -> randomCocktailDataError.value = error.message})
        )
    }
    fun getFavourites(){

        disposable.add(
            repository
                .getFavourites()
                .subscribe({data ->  favouritesData.value = data} , { error -> favouritesDataError.value = error.message })
        )
    }
    fun addFavourite(drink : Drink){

        disposable.add(
            repository
                .addFavourite(drink)
                .subscribe ({ favouritesDataSavedSuccess.value = true},{favouritesDataError.value = it.message})
        )
    }
    fun removeFavourite(drink : Drink){

        disposable.add(
            repository
                .removeFavourite(drink)
                .subscribe ({ favouritesDataSavedSuccess.value = false},{favouritesDataError.value = it.message})
        )
    }


    fun getRandomCocktailData() = randomCocktailData as LiveData<RandomDrink>
    fun getRandomCocktailDataError() = randomCocktailDataError as LiveData<String>

    fun getFavouritesData() = favouritesData as LiveData<List<Drink>>
    fun getFavouritesDataError() = favouritesDataError as LiveData<String>
    fun getFavouritesDataSaveSuccess() = favouritesDataSavedSuccess as LiveData<Boolean>

    override fun onCleared() {
        disposable.clear()
    }
}