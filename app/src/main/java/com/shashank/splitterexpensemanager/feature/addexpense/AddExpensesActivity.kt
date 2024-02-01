package com.shashank.splitterexpensemanager.feature.addexpense

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.localdb.model.Expenses
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddExpensesActivity : AppCompatActivity() {

    private val viewModel: AddExpensesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        lifecycleScope.launch {
            viewModel.insertAllExpenses(
                Expenses(null, 1, 1, 1, 100, "shashank", "10/10/2024", "10:10", "gyeruhwi"),
            )

            viewModel.expensesLiveData.observe(this@AddExpensesActivity) {
                Log.i("gyug", "onCreate: $it")
            }
        }
    }
}