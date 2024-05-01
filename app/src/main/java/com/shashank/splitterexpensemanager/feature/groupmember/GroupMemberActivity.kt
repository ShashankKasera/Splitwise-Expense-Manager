package com.shashank.splitterexpensemanager.feature.groupmember

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.GROUP_MEMBER
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GroupMemberActivity : AppCompatActivity() {
    @Inject
    lateinit var actionProcessor: ActionProcessor
    private val viewModel: GroupMemberViewModel by viewModels()
    private var groupId: Long = 0
    lateinit var recyclerView: RecyclerView
    lateinit var tvAddFriends: CardView
    private var groupMemberList = mutableListOf<Person>()
    lateinit var groupMemberAdapter: GroupMemberAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_member)

        groupId = intent.extras?.getLong(GROUP_ID) ?: 0
        groupId = intent.getLongExtra(GROUP_ID, 0)
        init()
        recyclerViewSetup()
        getGroupMember()
        tvAddFriends.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.ADD_FRIENDS.name,
                    hashMapOf(
                        GROUP_ID to (groupId)
                    )
                )
            )
        }
    }

    private fun init() {
        recyclerView = findViewById(R.id.rv_group_member)
        tvAddFriends = findViewById(R.id.cv_add_group_member)
    }

    private fun recyclerViewSetup() {
        groupMemberAdapter = GroupMemberAdapter(
            groupMemberList,
            object : GroupMemberAdapter.OnItemClickListener {
                override fun onItemClick(data: Person) {
                    val returnIntent = Intent()
                    returnIntent.putExtra(GROUP_MEMBER, data)
                    setResult(RESULT_OK, returnIntent)
                    finish()
                }
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = groupMemberAdapter
    }

    private fun getGroupMember() {
        viewModel.allGroupMember(groupId)
        lifecycleScope.launch {
            viewModel.allPerson.collect {
                if (it.isNotEmpty()) {
                    groupMemberList.clear()
                    groupMemberList.addAll(it)
                    groupMemberAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}