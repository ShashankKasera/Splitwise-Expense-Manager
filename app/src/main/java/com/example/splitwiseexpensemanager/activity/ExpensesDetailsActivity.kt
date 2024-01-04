package com.example.splitwiseexpensemanager.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splitwiseexpensemanager.R
import com.example.splitwiseexpensemanager.adapter.SplitAmountAdapter

class ExpensesDetailsActivity : AppCompatActivity() {
    lateinit var RecyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses_details)

        RecyclerView = findViewById(R.id.rv_group_activity)
        val splitAmountAdapter = SplitAmountAdapter()
        RecyclerView.layoutManager = LinearLayoutManager(this,)
        RecyclerView.adapter = splitAmountAdapter


    }
}