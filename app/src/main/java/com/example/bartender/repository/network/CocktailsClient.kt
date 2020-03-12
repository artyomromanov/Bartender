package com.example.bartender.repository.network

import com.example.bartender.INGREDIENTS_ENDPOINT
import com.example.bartender.RANDOM_ENDPOINT
import com.example.bartender.SEARCH_ENDPOINT
import com.example.bartender.favourites.model.RandomModel
import com.example.bartender.search.model.SearchModel
import com.example.bartender.shake.model.Ingredients
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailsClient {

    @GET(SEARCH_ENDPOINT)
    fun getSearchResults(@Query ("s") query : String) : Single<SearchModel>

    @GET(INGREDIENTS_ENDPOINT)
    fun getIngredients() : Single<Ingredients>

    @GET(RANDOM_ENDPOINT)
    fun getRandom() : Single<RandomModel>

}