package com.example.bartender

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.bartender.di.components.DaggerViewModelComponent
import com.example.bartender.di.modules.viewmodels.CocktailsViewModelModule
import com.example.bartender.util.MyApp
import com.example.bartender.viewmodel.CocktailsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.home_activity.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var model : CocktailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        supportActionBar?.hide()

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        val navController = host.navController

        setupBottomNavMenu(navController)

        DaggerViewModelComponent
            .builder()
            .appComponent((application as MyApp).component())
            .cocktailsViewModelModule(CocktailsViewModelModule(this))
            .build()
            .injectHomeActivity(this)

    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.cocktails_bottom_nav)
        bottomNav?.setupWithNavController(navController)
    }
}