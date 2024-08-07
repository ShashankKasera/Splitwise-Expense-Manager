package com.shashank.splitterexpensemanager.feature.addfriends

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.model.GroupMember
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddFriendsActivity : AppCompatActivity() {
    private val viewModel: AddFriendsViewModel by viewModels()
    private var personList = mutableListOf<Person>()
    private var groupMemberList = mutableListOf<GroupMember>()
    lateinit var recyclerView: RecyclerView
    lateinit var ivAddFriends: ImageView
    lateinit var addFriendsAdapter: AddFriendsAdapter
    lateinit var toolbar: TextView
    lateinit var ivBack: ImageView

    @Inject
    lateinit var actionProcessor: ActionProcessor

    @Inject
    lateinit var sharedPref: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friends)
        var groupId: Long = intent.extras?.getLong(GROUP_ID) ?: -1
        var personId: Long = sharedPref.getValue(PERSON_ID, 0L) as Long
        init()
        setUpRecyclerView(groupId, personList, groupMemberList)
        getAllPersonExcept(personId)
        getAllGroupMember()

        ivAddFriends.setOnClickListener {
            actionProcessor.process(ActionRequestSchema(ActionType.CREATE_FRIENDS.name))
        }
    }

    private fun init() {
        recyclerView = findViewById(R.id.rv_add_friends)
        ivAddFriends = findViewById(R.id.tv_add_friend)
        toolbar = findViewById(R.id.tv_tb_add_friends)
        ivBack = findViewById(R.id.iv_tb_add_friends)

        toolbar.text = getString(R.string.add_friends)
        ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setUpRecyclerView(
        groupId: Long,
        personList: List<Person>,
        groupMemberList: List<GroupMember>
    ) {
        addFriendsAdapter = AddFriendsAdapter(
            groupId,
            personList,
            groupMemberList,
            object : AddFriendsAdapter.OnItemClickListener {
                override fun onItemClick(
                    position: Int,
                    data: Person,
                    isChecked: Boolean
                ) {
                    if (isChecked) {
                        viewModel.insertGroupMember(data.id ?: 0, groupId)
                    } else {
                        viewModel.deleteGroupMember(data.id ?: 0)
                    }
                }
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(this@AddFriendsActivity)
        recyclerView.adapter = addFriendsAdapter
    }

    private fun getAllPersonExcept(personId: Long) {
        viewModel.getAllPersonExcept(personId)
        lifecycleScope.launch {
            viewModel.allPersonExcept.collect {
                if (it.isNotEmpty()) {
                    personList.clear()
                    personList.addAll(it)
                    addFriendsAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun getAllGroupMember() {
        viewModel.getAllGroupMember()
        lifecycleScope.launch {
            viewModel.allGroupMember.collect { groupMember ->

                if (groupMember.isNotEmpty()) {
                    groupMemberList.clear()
                    groupMemberList.addAll(groupMember)
                    addFriendsAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}

