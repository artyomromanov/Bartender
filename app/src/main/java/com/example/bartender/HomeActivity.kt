package com.example.bartender

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bartender.favourites.FavouritesFragment
import com.example.bartender.search.di.components.DaggerAppComponent
import com.example.bartender.search.di.components.DaggerViewModelComponent
import com.example.bartender.search.di.modules.ViewModelModule
import kotlinx.android.synthetic.main.home_activity.*
import com.example.bartender.search.view.SearchFragment
import com.example.bartender.search.viewmodel.SearchViewModel
import com.example.bartender.shake.ShakeFragment
import javax.inject.Inject

class HomeActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        supportActionBar?.hide()

        cocktails_navigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {

                R.id.id_search -> {
                    createFragment(SearchFragment())
                }
                R.id.id_favourites -> {
                    createFragment(FavouritesFragment())
                }
                R.id.id_builder -> {
                    createFragment(ShakeFragment())
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