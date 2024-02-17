package com.shashank.splitterexpensemanager.feature.addfriends

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.model.GroupMember
import com.shashank.splitterexpensemanager.localdb.model.GroupMember as GroupMemberEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddFriendsActivity : AppCompatActivity() {
    private val viewModel: AddFriendsViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    lateinit var tvAddFriends: TextView

    @Inject
    lateinit var actionProcessor: ActionProcessor

    @Inject
    lateinit var sharedPref: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friends)
        var groupId: Long = intent.extras?.getLong(GROUP_ID) ?: 0
        var personId: Long = sharedPref.getValue(PERSON_ID, 0L) as Long
        recyclerView = findViewById(R.id.rv_add_friends)
        tvAddFriends = findViewById(R.id.tv_add_User)

        tvAddFriends.setOnClickListener {
            actionProcessor.process(ActionRequestSchema(ActionType.CREATE_FRIENDS.name))
        }

        viewModel.getAllPersonExcept(personId)
        lifecycleScope.launch {
            viewModel.allPersonExcept.collect { person ->
                viewModel.getAllGroupMember()
                lifecycleScope.launch {
                    viewModel.allGroupMember.collect { groupMember ->
                        recyclerViewSetUp(groupId, person, groupMember)
                    }
                }
            }
        }
    }

    private fun recyclerViewSetUp(
        groupId: Long,
        person: List<Person>,
        groupMember: List<GroupMember>
    ) {
        val addFriendsAdapter = AddFriendsAdapter(
            groupId,
            person,
            groupMember,
            object : AddFriendsAdapter.OnItemClickListener {
                override fun onItemClick(
                    position: Int,
                    data: Person,
                    isChecked: Boolean
                ) {
                    lifecycleScope.launch {
                        if (isChecked) {
                            viewModel.insertGroupMember(
                                GroupMemberEntity(
                                    null,
                                    data.id ?: 0,
                                    groupId
                                )
                            )
                        } else {
                            viewModel.deleteGroupMember(data.id ?: 0)
                        }
                    }
                }
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(this@AddFriendsActivity)
        recyclerView.adapter = addFriendsAdapter
    }
}

