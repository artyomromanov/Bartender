package com.example.bartender.search.view

import android.view.View
import com.example.bartender.search.model.Drink

interface RecyclerViewClickListener {

    fun onCocktailItemClicked(view : View, drink: Drink)

    fun onSuggestionItemClicked(suggestion : String)

}