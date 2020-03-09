package com.example.bartender.search.model

import com.example.bartender.search.model.Drink
import io.reactivex.Single

interface Repository {

    fun getSearchResults(query : String) : Single<List<Drink>>

}
