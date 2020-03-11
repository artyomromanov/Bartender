package com.example.bartender

import android.app.Application
import com.example.bartender.di.components.AppComponent
import com.example.bartender.di.components.DaggerAppComponent
import com.example.bartender.di.modules.DatabaseModule
import com.example.bartender.di.modules.NetworkModule

class MyApp : Application(){

    override fun onCreate() {
        component().inject(this)
        super.onCreate()

    }
    fun component() : AppComponent {

        return DaggerAppComponent
            .builder()
            .networkModule(NetworkModule())
            .databaseModule(DatabaseModule(this))
            .build()

    }
}