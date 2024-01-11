package com.example.authentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.splitwiseexpensemanager.authentication.R

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar?.hide()
    }
}