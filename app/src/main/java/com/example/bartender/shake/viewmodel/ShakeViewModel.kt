package com.example.bartender.shake.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bartender.Repository
import com.example.bartender.search.model.Drink
import com.example.bartender.shake.model.Ingredient

class ShakeViewModel(private val repository: Repository.Shake) : ViewModel() {

    private val disposable = io.reactivex.disposables.CompositeDisposable()

    private var ingredientData = MutableLiveData<List<Ingredient>>()
    private val ingredientErrorData = MutableLiveData<String>()

    private val savedSearchSuggestions = MutableLiveData<List<String>>()

    private val favouritesData = MutableLiveData<List<Drink>>()


    fun getIngredients() {
        disposable.add(
            repository
                .getIngredients()
                .subscribe({ data -> ingredientData.value = data },
                    { error -> ingredientErrorData.value = error.message })
        )
    }

    fun getIngredientData() = ingredientData as LiveData<List<Ingredient>>
    fun getIngredientErrorData() = ingredientErrorData as LiveData<String>

}