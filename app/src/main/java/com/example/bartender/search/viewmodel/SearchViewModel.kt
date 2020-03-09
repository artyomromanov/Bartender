package com.example.bartender.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bartender.search.model.Drink
import com.example.bartender.search.model.Repository
import com.example.bartender.search.model.RepositoryImpl

class SearchViewModel(private val repository : Repository) : ViewModel(){

    private val disposable = io.reactivex.disposables.CompositeDisposable()

    private val searchData = MutableLiveData<List<Drink>>()
    private val errorData = MutableLiveData<String>()

    fun getSearchResults(query : String){

        disposable.add(

            repository
            .getSearchResults(query)
            .subscribe ({ data ->

                searchData.value = data
                println(data[0].strDrink)

            }, { error -> errorData.value = error.message})

        )
    }

    fun getSearchData() = searchData as LiveData<List<Drink>>
    fun getErrorData() = errorData as LiveData<String>

}