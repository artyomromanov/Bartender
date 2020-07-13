package com.example.bartender.favourites_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bartender.R
import com.example.bartender.util.dummyDrink
import com.example.bartender.viewmodel.CocktailsViewModel
import kotlinx.android.synthetic.main.favourites_fragment.*
import kotlinx.android.synthetic.main.search_fragment.*

class FavouritesFragment : Fragment(), FavouritesRecyclerViewClickListener {

    private lateinit var viewModel : CocktailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.favourites_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(CocktailsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        rv_favourites.layoutManager = LinearLayoutManager(this.context)

        viewModel.addFavourite(dummyDrink)

        status(1)

        viewModel.getFavourites()

        viewModel.getFavouritesData().observe(viewLifecycleOwner, Observer {

            rv_favourites.adapter = FavouritesAdapter(it, this@FavouritesFragment)
            status(2)

        })

        viewModel.getFavouritesDataError().observe(viewLifecycleOwner, Observer {

            favourites_tv_error.text = it
            status(3)

        })

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

                    viewModel.getFavourites()
                    status(1)

                }
            }
        }
    }

    override fun onFavouritesItemClicked(view: View) {

    }

}