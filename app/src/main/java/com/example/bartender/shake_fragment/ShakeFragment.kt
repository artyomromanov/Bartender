package com.example.bartender.shake_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bartender.R
import com.example.bartender.model.Drink
import com.example.bartender.viewmodel.CocktailsViewModel
import kotlinx.android.synthetic.main.shake_fragment.*

class ShakeFragment : Fragment() {

    private lateinit var viewModel: CocktailsViewModel
    private var favouritesList: List<Drink> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.shake_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(CocktailsViewModel::class.java)
        } ?: throw Exception("Invalid Activity!")

        viewModel.getFavourites()


        viewModel.getFavouritesData().observe(viewLifecycleOwner, Observer {
            favouritesList = it
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, favouritesList)
            with(adapter){
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                ingredient_spinner.adapter = this
            }
        })
    }
}