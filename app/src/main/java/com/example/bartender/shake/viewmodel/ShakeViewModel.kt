package com.example.bartender.shake.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bartender.Repository
import com.example.bartender.shake.model.Ingredients

class ShakeViewModel(private val repository: Repository.Shake) : ViewModel() {

    private val disposable = io.reactivex.disposables.CompositeDisposable()

    private var ingredientData = MutableLiveData<Ingredients>()
    private val ingredientNetworkErrorData = MutableLiveData<String>()

    private val databaseErrorData = MutableLiveData<String>()

    private val databaseSuccessData = MutableLiveData<Boolean>()



    //Get online ingredients and cache them to DB
    fun getAndCacheIngredients() {
        disposable.add(
            repository
                .getIngredientsOnline()
                .subscribe(
                    { data ->  ingredientData.value = data
                    repository.cacheIngredients(data).subscribe(

                        { databaseSuccessData.value = true },
                        { databaseErrorData.value = it.message })},

                    { error -> ingredientNetworkErrorData.value = error.message })
        )
    }


    fun getIngredientData() = ingredientData as LiveData<Ingredients>
    fun getIngredientNetworkErrorData() = ingredientNetworkErrorData as LiveData<String>
    fun getDatabaseErrorData() = databaseErrorData as LiveData<String>
    fun getCacheSavedSuccessData() = databaseSuccessData as LiveData<Boolean>

}