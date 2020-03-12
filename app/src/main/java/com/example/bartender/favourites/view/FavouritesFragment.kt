package com.example.bartender.favourites.view

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bartender.MyApp
import com.example.bartender.R
import com.example.bartender.di.components.DaggerFavouritesViewModelComponent
import com.example.bartender.di.modules.viewmodels.FavouritesViewModelModule
import com.example.bartender.dummyDrink
import com.example.bartender.favourites.model.RandomDrink
import com.example.bartender.favourites.viewmodel.FavouritesViewModel
import com.squareup.picasso.Picasso
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

        with(favouritesViewModel){

            addFavourite(dummyDrink)

            status(1)

            getFavourites()

            getFavouritesData().observe(viewLifecycleOwner, Observer {

                rv_favourites.adapter = FavouritesAdapter(it, this@FavouritesFragment)
                status(2)

            })

            getFavouritesDataError().observe(viewLifecycleOwner, Observer {

                favourites_tv_error.text = it
                status(3)

            })

            getRandomCocktailData().observe(viewLifecycleOwner, Observer {
                    bind(it)
            })

            getRandomCocktailDataError().observe(viewLifecycleOwner, Observer {
                favourites_tv_error.text = it
            })

            favourites_random_btn_get.setOnClickListener {
                getRandomCocktail()
            }
        }
    }

    private fun bind(data : RandomDrink){

        favourites_pb_random_progress.visibility = View.VISIBLE
        favourites_random_tv_name.text = data.strDrink
        favourites_random_tv_description.text = data.strInstructions
        favourites_random_tv_description.movementMethod = ScrollingMovementMethod()

        Picasso.get().load(data.strDrinkThumb).into(favourites_random_iv_thumb, object : com.squareup.picasso.Callback {
            override fun onSuccess() {
                favourites_pb_random_progress.visibility = View.GONE
                favourites_random_tv_name.visibility = View.VISIBLE
                favourites_random_iv_thumb.visibility = View.VISIBLE
                favourites_random_tv_description.visibility = View.VISIBLE
            }
            override fun onError(e: Exception?) {
                favourites_random_tv_name.text = getString(R.string.txt_random_image_error)
                favourites_random_tv_description.text = getString(R.string.txt_random_image_error_desc)
            }
        })

    }

    private fun initializeViewModel() {
        DaggerFavouritesViewModelComponent
            .builder()
            .appComponent((activity?.application as MyApp).component())
            .favouritesViewModelModule(FavouritesViewModelModule(this))
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