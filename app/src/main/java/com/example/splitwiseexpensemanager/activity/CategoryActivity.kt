package com.example.splitwiseexpensemanager.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splitwiseexpensemanager.R
import com.example.splitwiseexpensemanager.adapter.CategoryAdapter
import com.example.splitwiseexpensemanager.adapter.GroupAdapter

class CategoryActivity : AppCompatActivity() {

    lateinit var RecyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        RecyclerView = findViewById(R.id.rv_category)
        val categoryAdapter = CategoryAdapter()
        RecyclerView.layoutManager = LinearLayoutManager(this,)
        RecyclerView.adapter = categoryAdapter
    }
}