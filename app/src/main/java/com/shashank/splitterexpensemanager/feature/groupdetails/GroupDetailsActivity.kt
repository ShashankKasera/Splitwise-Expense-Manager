package com.shashank.splitterexpensemanager.feature.groupdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GroupDetailsActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var tvGroupName: TextView

    @Inject
    lateinit var sharedPref: SharedPref
    private val viewModel: GroupDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_details)
        var groupId: Long = intent.extras?.getLong(GROUP_ID) ?: 0
        init()
        getData(groupId)
    }

    private fun init() {
        recyclerView = findViewById(R.id.rv_group_activity)
        tvGroupName = findViewById(R.id.tv_group_Name_in_detail)
    }

    private fun getData(groupId: Long) {
        lifecycleScope.launch {
            viewModel.getGroup(groupId)
            viewModel.group.collect {
                if (it != null) tvGroupName.text = it.groupName
            }
        }
    }
}