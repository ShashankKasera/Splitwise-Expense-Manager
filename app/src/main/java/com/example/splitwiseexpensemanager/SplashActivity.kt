package com.example.splitwiseexpensemanager

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.authentication.LoginActivity

private const val SPLASH_DELAY: Long = 20000
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        Handler().postDelayed({
            // Start the main activity or another activity here
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Finish the splash activity so the user can't go back to it
        }, SPLASH_DELAY)
    }
}