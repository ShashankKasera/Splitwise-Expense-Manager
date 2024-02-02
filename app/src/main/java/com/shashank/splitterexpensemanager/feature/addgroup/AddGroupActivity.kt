package com.shashank.splitterexpensemanager.feature.addgroup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R

class AddGroupActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)

        recyclerView = findViewById(R.id.rv_group_member)
        val addGroupMemberAdapter = AddGroupMemberAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = addGroupMemberAdapter
    }
}