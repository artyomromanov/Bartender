package com.example.bartender

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bartender.favourites.view.FavouritesFragment
import com.example.bartender.search.view.SearchFragment
import com.example.bartender.shake.view.ShakeFragment
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        supportActionBar?.hide()

        var currentlySelectedId = cocktails_navigation.selectedItemId

        cocktails_navigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {

                R.id.id_search -> {
                    if (currentlySelectedId != R.id.id_search) {
                        createFragment(SearchFragment())
                        currentlySelectedId = R.id.id_search
                    }
                }
                R.id.id_favourites -> {
                    if (currentlySelectedId != R.id.id_favourites) {
                        createFragment(FavouritesFragment())
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

        createFragment(SearchFragment())

    }

    private fun createFragment(fragment: Fragment) {

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment).commit()

    }
}