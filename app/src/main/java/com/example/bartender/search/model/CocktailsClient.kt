package com.example.bartender.search.model

import com.example.bartender.SEARCH_ENDPOINT
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailsClient {

    @GET(SEARCH_ENDPOINT)
    fun getSearchResults(@Query ("s") query : String) : Single<SearchModel>

}