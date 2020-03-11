package com.example.bartender.search.view

import android.view.View

interface RecyclerViewClickListener {

    fun onCocktailItemClicked(view : View)

    fun onSuggestionItemClicked(suggestion : String)

}