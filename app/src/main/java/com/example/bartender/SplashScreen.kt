package com.example.bartender

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {

    companion object {
        const val SPLASH_TIME_OUT: Long = 1
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
