package com.shashank.splitterexpensemanager.feature.groupdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.ID
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GroupDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var actionProcessor: ActionProcessor
    lateinit var recyclerView: RecyclerView
    lateinit var tvGroupName: TextView
    lateinit var tvAddExpenses: TextView
    lateinit var llAddGroupMember: LinearLayout

    private val viewModel: GroupDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_details)
        var id: Long = intent.extras?.getLong(ID) ?: 0
        init()
        navigationForAddFriends(id)
        navigationForGroupMember(id)
        navigationForAddExpenses(id)
        getData(id)
    }

    private fun init() {
        recyclerView = findViewById(R.id.rv_group_activity)
        tvGroupName = findViewById(R.id.tv_group_Name_in_detail)
        tvAddExpenses = findViewById(R.id.tv_add_expenses)
        llAddGroupMember = findViewById(R.id.ll_group_member)
    }

    private fun navigationForAddFriends(id: Long) {
        llAddGroupMember.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.ADD_FRIENDS.name,
                    hashMapOf(
                        ID to (id)
                    )
                )
            )
        }
    }

    private fun navigationForGroupMember(id: Long) {
        tvGroupName.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.GROUP_MEMBER.name,
                    hashMapOf(
                        ID to (id)
                    )
                )
            )
        }
    }

    private fun navigationForAddExpenses(id: Long) {
        tvAddExpenses.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.ADD_EXPENSES.name,
                    hashMapOf(
                        ID to (id)
                    )
                )
            )
        }
    }

    private fun getData(id: Long) {
        lifecycleScope.launch {
            viewModel.groupLiveData(id).observe(this@GroupDetailsActivity) {
                tvGroupName.text = it.groupName
            }
        }
    }
}