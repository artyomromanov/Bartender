package com.example.bartender.model

import com.example.bartender.util.SEARCH_ENDPOINT
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailsClient {

    @GET(SEARCH_ENDPOINT)
    fun getSearchResults(@Query ("s") query : String) : Single<SearchModel>

}