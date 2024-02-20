package com.shashank.splitterexpensemanager.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.feature.account.AccountFragment
import com.shashank.splitterexpensemanager.feature.activity.ActivityFragment
import com.shashank.splitterexpensemanager.feature.friends.FriendsFragment
import com.shashank.splitterexpensemanager.feature.group.GroupFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

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