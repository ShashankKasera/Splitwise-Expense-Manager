package com.example.splitwiseexpensemanager.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splitwiseexpensemanager.R
import com.example.splitwiseexpensemanager.adapter.ActivityAdapter

class GroupDetailsActivity : AppCompatActivity() {

    lateinit var RecyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_details)

        RecyclerView = findViewById(R.id.rv_group_activity)
        val activityAdapter = ActivityAdapter()
        RecyclerView.layoutManager = LinearLayoutManager(this,)
        RecyclerView.adapter = activityAdapter
    }
}