package com.example.bartender.search_fragment

import android.view.View
import com.example.bartender.model.Drink

interface RecyclerViewClickListener {

    fun onCocktailItemClicked(view : View, drink: Drink)

    fun onSuggestionItemClicked(suggestion : String)

}