package com.shashank.splitterexpensemanager.feature.groupmember

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupMemberActivity : AppCompatActivity() {
    private val viewModel: GroupMemberViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_member)
        recyclerView = findViewById(R.id.rv_group_member)
        val id = intent.extras?.getLong(ID)

        viewModel.allGroupLiveData(id ?: 0).observe(this@GroupMemberActivity) {
            var groupMemberAdapter = GroupMemberAdapter(it)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = groupMemberAdapter
        }
    }
}