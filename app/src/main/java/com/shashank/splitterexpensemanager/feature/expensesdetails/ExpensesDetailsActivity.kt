package com.shashank.splitterexpensemanager.feature.expensesdetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R

class ExpensesDetailsActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses_details)

        recyclerView = findViewById(R.id.rv_group_expense)
        val splitAmountAdapter = SplitAmountAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = splitAmountAdapter
    }
}