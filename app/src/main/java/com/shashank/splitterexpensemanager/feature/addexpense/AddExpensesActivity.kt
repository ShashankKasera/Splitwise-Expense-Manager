package com.shashank.splitterexpensemanager.feature.addexpense

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shashank.splitterexpensemanager.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddExpensesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)
    }
}