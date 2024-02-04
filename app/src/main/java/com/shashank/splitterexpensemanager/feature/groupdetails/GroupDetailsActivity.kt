package com.shashank.splitterexpensemanager.feature.groupdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.ID
import com.shashank.splitterexpensemanager.feature.activity.ActivityAdapter
import com.shashank.splitterexpensemanager.feature.group.GroupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class GroupDetailsActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var tvGroupName: TextView
    private val viewModel: GroupViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_details)

        val id = intent.extras?.getLong(ID)
        Log.i("njge", "onCreate: $id")
        recyclerView = findViewById(R.id.rv_group_activity)
        tvGroupName=findViewById(R.id.tv_group_Name_in_detail)
        lifecycleScope.launch {
            viewModel.groupLiveData(id?:0).observe(this@GroupDetailsActivity) {
                tvGroupName.text=it.groupName
            }
        }
        val activityAdapter = ActivityAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = activityAdapter
    }
}