package com.shashank.splitterexpensemanager.feature.friendsdetails

import android.os.Bundle
import android.widget.TextView
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
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.model.FriendOweOrOwed
import com.shashank.splitterexpensemanager.model.Group
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FriendsDetailsActivity : AppCompatActivity() {
    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var actionProcessor: ActionProcessor
    lateinit var rvGroupOweOwed: RecyclerView
    lateinit var tvFriendName: TextView
    lateinit var tvOverallOweOrOwed: TextView
    lateinit var tvPlus: TextView
    lateinit var rvFriendOweOwed: RecyclerView
    lateinit var groupOweOwedAdapter: GroupOweOwedAdapter
    lateinit var friendOweOwedAdapter: FriendOweOwedAdapter
    private var groupOweOwedList = mutableListOf<Pair<Group, Double>>()
    private var friendOweOwedList = mutableListOf<FriendOweOrOwed>()

    private val viewModel: FriendsDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_details)
        var friendId: Long = intent.extras?.getLong(FRIEND_ID) ?: -1
        val personId: Long = sharedPref.getValue(PERSON_ID, 0L) as Long

        init(friendId, personId)
    }

    private fun init(friendId: Long, personId: Long) {
        rvGroupOweOwed = findViewById(R.id.rv_group_owe_owed_friend_details)
        tvFriendName = findViewById(R.id.tv_friends_Name)
        tvOverallOweOrOwed = findViewById(R.id.tv_overall_owe_friend_details)
        rvFriendOweOwed = findViewById(R.id.rv_owe_owed_friend_details)
        tvPlus = findViewById(R.id.tv_other_member_friend_details)
        recyclerViewSetUp()

        viewModel.loadAllFriends(personId, friendId)

        getData()
    }

    private fun recyclerViewSetUp() {
        groupOweOwedAdapter = GroupOweOwedAdapter(actionProcessor, groupOweOwedList)
        rvGroupOweOwed.layoutManager = LinearLayoutManager(this)
        rvGroupOweOwed.adapter = groupOweOwedAdapter

        friendOweOwedAdapter = FriendOweOwedAdapter(friendOweOwedList)
        rvFriendOweOwed.layoutManager = LinearLayoutManager(this)
        rvFriendOweOwed.adapter = friendOweOwedAdapter
    }

    private fun getData() {
        lifecycleScope.launch {
            viewModel.allFriends.collect {
                tvFriendName.text = it.friend.name
                if (it.friendsHashMap.isNotEmpty()) {
                    groupOweOwedList.clear()
                    groupOweOwedList.addAll(it.friendsHashMap.toList())
                    groupOweOwedAdapter.notifyDataSetChanged()
                }
                if (it.friendOweOwedList.isNotEmpty()) {
                    friendOweOwedList.clear()
                    friendOweOwedList.addAll(it.friendOweOwedList)
                    friendOweOwedAdapter.notifyDataSetChanged()
                    overall(friendOweOwedList.size, it.overallOweOrOwed)
                }
                if (friendOweOwedList.size > 3) {
                    tvPlus.visible()
                    tvPlus.text =
                        getString(R.string.plus_other_balance, (friendOweOwedList.size - 2))
                } else {
                    tvPlus.gone()
                }
            }
        }
    }

    private fun overall(size: Int, overallOweOrOwed: Double) {
        if (size == 0) {
            tvOverallOweOrOwed.visible()
            tvOverallOweOrOwed.text = getString(R.string.you_are_all_settled_up_in_this_group)
            tvOverallOweOrOwed.setTextColor(tvOverallOweOrOwed.context.resources.getColor(R.color.black))
        } else if (size > 1) {
            tvOverallOweOrOwed.visible()
            val overall = overallOweOrOwed

            val colorResId =
                if (overall == 0.0) R.color.black else if (overall < 0) R.color.primary_dark else R.color.green
            val absOverall = Math.abs(overall)

            tvOverallOweOrOwed.text = if (overall == 0.0) {
                getString(R.string.you_are_settled_up_overall)
            } else if (overall < 0) {
                getString(R.string.you_owe_rs_overall, absOverall.formatNumber(2))
            } else {
                getString(R.string.you_are_owed_rs_overall, absOverall.formatNumber(2))
            }

            tvOverallOweOrOwed.setTextColor(tvOverallOweOrOwed.context.resources.getColor(colorResId))
        } else {
            tvOverallOweOrOwed.gone()
        }
    }
}