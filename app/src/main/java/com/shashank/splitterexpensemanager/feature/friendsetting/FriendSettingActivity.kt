package com.shashank.splitterexpensemanager.feature.friendsetting

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.FRIEND_ID
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.model.Group
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FriendSettingActivity : AppCompatActivity() {
    lateinit var tvFriendName: TextView
    lateinit var recyclerView: RecyclerView
    private val viewModel: FriendSettingViewModel by viewModels()
    private var groupList = mutableListOf<Group>()
    lateinit var groupAdapter: GroupAdapter

    @Inject
    lateinit var actionProcessor: ActionProcessor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_setting)
        var friendId: Long = intent.extras?.getLong(FRIEND_ID) ?: -1

        tvFriendName = findViewById(R.id.tv_name_friend_setting)
        recyclerView = findViewById(R.id.rv_friend_settings)
        setUpRecyclerView()
        viewModel.allGroup(friendId)
        lifecycleScope.launch {
            viewModel.allGroupList.collect {
                groupList.clear()
                groupList.addAll(it)
                groupAdapter.notifyDataSetChanged()
            }
        }
        viewModel.loadFriend(friendId)
        lifecycleScope.launch {
            viewModel.friend.collect {
                tvFriendName.text = it.name
            }
        }
    }

    private fun setUpRecyclerView() {
        groupAdapter = GroupAdapter(actionProcessor, groupList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = groupAdapter
    }
}