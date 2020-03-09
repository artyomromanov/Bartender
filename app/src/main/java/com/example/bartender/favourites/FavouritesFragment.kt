package com.example.bartender.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bartender.MyApp
import com.example.bartender.R
import com.example.bartender.search.di.components.DaggerViewModelComponent
import com.example.bartender.search.di.modules.ViewModelModule
import com.example.bartender.search.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.favourites_fragment.*
import javax.inject.Inject

class FavouritesFragment : Fragment(){

    @Inject
    lateinit var model : SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.favourites_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_favourites.layoutManager = LinearLayoutManager(this.context)

        DaggerViewModelComponent
            .builder()
            .appComponent((activity?.application as MyApp)
                .component())
            .viewModelModule(ViewModelModule(this)).build()
            .injectFavouritesFragment(this)

    }
}