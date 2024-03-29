package com.example.splitwiseexpensemanager.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.splitwiseexpensemanager.R
import com.example.splitwiseexpensemanager.fragments.AccountFragment
import com.example.splitwiseexpensemanager.fragments.ActivityFragment
import com.example.splitwiseexpensemanager.fragments.FriendsFragment
import com.example.splitwiseexpensemanager.fragments.GroupFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(GroupFragment())
        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.group -> {
                    loadFragment(GroupFragment())
                    true
                }

                R.id.friends -> {
                    loadFragment(FriendsFragment())
                    true
                }

                R.id.activity -> {
                    loadFragment(ActivityFragment())
                    true
                }

                else -> {
                    loadFragment(AccountFragment())
                    true
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}