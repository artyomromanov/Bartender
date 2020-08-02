package com.example.bartender.util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.bartender.HomeActivity
import com.example.bartender.R

class SplashScreen : AppCompatActivity() {

    companion object {
        const val SPLASH_TIME_OUT: Long = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        supportActionBar?.hide()

        Handler().postDelayed(
            {startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }, SPLASH_TIME_OUT
        )
    }
}
