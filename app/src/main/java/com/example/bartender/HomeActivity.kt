package com.example.bartender

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bartender.di.components.DaggerViewModelComponent
import com.example.bartender.di.modules.viewmodels.CocktailsViewModelModule
import com.example.bartender.favourites_fragment.FavouritesFragment
import com.example.bartender.search_fragment.SearchFragment
import com.example.bartender.shake_fragment.ShakeFragment
import com.example.bartender.util.MyApp
import com.example.bartender.viewmodel.CocktailsViewModel
import kotlinx.android.synthetic.main.home_activity.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var model : CocktailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        supportActionBar?.hide()

        DaggerViewModelComponent
            .builder()
            .appComponent((application as MyApp).component())
            .cocktailsViewModelModule(CocktailsViewModelModule(this))
            .build()
            .injectHomeActivity(this)

        var currentlySelectedId = cocktails_navigation.selectedItemId

        cocktails_navigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {

                R.id.id_search -> {
                    if (currentlySelectedId != R.id.id_search) {
                        createFragment(SearchFragment(model))
                        currentlySelectedId = R.id.id_search
                    }
                }
                R.id.id_favourites -> {
                    if (currentlySelectedId != R.id.id_favourites) {
                        createFragment(FavouritesFragment(model))
                        currentlySelectedId = R.id.id_favourites
                    }
                }
                R.id.id_builder -> {
                    if (currentlySelectedId != R.id.id_builder) {
                        createFragment(ShakeFragment())
                        currentlySelectedId = R.id.id_builder
                    }
                }
                else -> throw IllegalArgumentException()
            }
            true
        }

        createFragment(SearchFragment(model))

    }

    private fun createFragment(fragment: Fragment) {

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment).commit()

    }
}