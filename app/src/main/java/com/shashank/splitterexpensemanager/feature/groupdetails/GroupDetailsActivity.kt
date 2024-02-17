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
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.feature.activity.ActivityAdapter
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GroupDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var actionProcessor: ActionProcessor
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


        val id = intent.extras?.getLong(ID)
        recyclerView = findViewById(R.id.rv_group_activity)
        tvGroupName = findViewById(R.id.tv_group_Name_in_detail)
        llAddGroupMember = findViewById(R.id.ll_group_member)
        tvGroupName.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.GROUP_MEMBER.name,
                    hashMapOf(
                        ID to (id ?: 0L)
                    )
                )
            )
        }
        llAddGroupMember.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.ADD_FRIENDS.name,
                    hashMapOf(
                        ID to (id ?: 0L)
                    )
                )
            )
        }
        lifecycleScope.launch {
            viewModel.getGroup(groupId)
            viewModel.group.collect {
                if (it != null) tvGroupName.text = it.groupName
            }
        }
        val activityAdapter = ActivityAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = activityAdapter
    }
}