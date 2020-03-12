package com.example.bartender.favourites.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bartender.MyApp
import com.example.bartender.R
import com.example.bartender.di.components.DaggerViewModelComponent
import com.example.bartender.di.modules.viewmodels.FavouritesViewModelModule
import com.example.bartender.di.modules.viewmodels.SearchViewModelModule
import com.example.bartender.di.modules.viewmodels.ShakeViewModelModule
import com.example.bartender.dummyDrink
import com.example.bartender.favourites.viewmodel.FavouritesViewModel
import kotlinx.android.synthetic.main.favourites_fragment.*
import kotlinx.android.synthetic.main.search_fragment.*
import javax.inject.Inject

class FavouritesFragment : Fragment(), FavouritesRecyclerViewClickListener {

    @Inject
    lateinit var favouritesViewModel: FavouritesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.favourites_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_favourites.layoutManager = LinearLayoutManager(this.context)

        initializeViewModel()

        favouritesViewModel.addFavourite(dummyDrink)

        status(1)

        favouritesViewModel.getFavourites()

        favouritesViewModel.getFavouritesData().observe(viewLifecycleOwner, Observer {

            rv_favourites.adapter = FavouritesAdapter(it, this@FavouritesFragment)
            status(2)

        })

        favouritesViewModel.getFavouritesDataError().observe(viewLifecycleOwner, Observer {

            favourites_tv_error.text = it
            status(3)

        })

    }

    private fun initializeViewModel() {
        DaggerViewModelComponent
            .builder()
            .appComponent((activity?.application as MyApp).component())
            .favouritesViewModelModule(FavouritesViewModelModule(this))
            .searchViewModelModule(SearchViewModelModule(this))
            .shakeViewModelModule(ShakeViewModelModule(this))
            .build()
            .injectFavouritesFragment(this)
    }

    private fun status(state: Int) {

        when (state) {

            1 -> { //Initial state - loading

                favourites_status_container.visibility = View.VISIBLE
                favourites_tv_error.visibility = View.GONE
                favourites_pb_progress.visibility = View.VISIBLE
                favourites_btn_retry.visibility = View.GONE

            }

            2 -> {  //Network Success
                favourites_status_container.visibility = View.GONE
                favourites_pb_progress.visibility = View.GONE
                favourites_btn_retry.visibility = View.GONE
                favourites_tv_error.visibility = View.GONE
                rv_favourites.visibility = View.VISIBLE

            }

            3 -> {  //Error
                favourites_status_container.visibility = View.VISIBLE
                favourites_pb_progress.visibility = View.GONE
                favourites_btn_retry.visibility = View.VISIBLE
                favourites_tv_error.visibility = View.VISIBLE
                rv_search.visibility = View.GONE

                favourites_btn_retry.setOnClickListener {

                    favouritesViewModel.getFavourites()
                    status(1)

                }
            }
        }
    }

    override fun onFavouritesItemClicked(view: View) {

    }

}