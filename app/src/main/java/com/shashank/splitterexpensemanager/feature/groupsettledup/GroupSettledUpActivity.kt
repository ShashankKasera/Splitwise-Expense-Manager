package com.shashank.splitterexpensemanager.feature.groupsettledup

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.FRIEND_ID
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.model.FriendOweOrOwed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GroupSettledUpActivity : AppCompatActivity() {
    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var actionProcessor: ActionProcessor
    lateinit var recyclerView: RecyclerView
    lateinit var groupSettleUpAdapter: GroupSettleUpAdapter
    private var settledUpList = mutableListOf<FriendOweOrOwed>()
    private val viewModel: GroupSettledUpViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_settled_up)
        var friendId: Long = intent.extras?.getLong(FRIEND_ID) ?: -1
        val personId: Long = sharedPref.getValue(PERSON_ID, 0L) as Long
        recyclerView = findViewById(R.id.rv_group_settle_up)
        setupRecyclerView(personId)
        viewModel.loadAllFriends(personId, friendId)
        getData()
    }

    override fun onRestart() {
        super.onRestart()
        var friendId: Long = intent.extras?.getLong(FRIEND_ID) ?: -1
        val personId: Long = sharedPref.getValue(PERSON_ID, 0L) as Long
        viewModel.loadAllFriends(personId, friendId)
    }

    private fun setupRecyclerView(personId: Long) {
        groupSettleUpAdapter = GroupSettleUpAdapter(personId, actionProcessor, settledUpList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = groupSettleUpAdapter
    }

    private fun getData() {
        lifecycleScope.launch {
            viewModel.allFriends.collect {
                settledUpList.apply {
                    clear()
                    addAll(it)
                }
                groupSettleUpAdapter.notifyDataSetChanged()
            }
        }
    }
}