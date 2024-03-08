package com.shashank.splitterexpensemanager.feature.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.model.Friends
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FriendsFragment : Fragment() {

    @Inject
    lateinit var actionProcessor: ActionProcessor

    @Inject
    lateinit var sharedPref: SharedPref
    lateinit var recyclerView: RecyclerView
    lateinit var tvOverall: TextView
    lateinit var cvAddFriend: CardView
    lateinit var friendAdapter: FriendAdapter
    private val viewModel: FriendsViewModel by viewModels()
    private var allFriendsList = mutableListOf<Friends>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_friends, container, false)
        val personId: Long = sharedPref.getValue(PERSON_ID, 0L) as Long

        init(v)
        setUpRecyclerView()
        viewModel.loadAllFriends(personId)
        getData()

        cvAddFriend.setOnClickListener {
            actionProcessor.process(ActionRequestSchema(ActionType.CREATE_FRIENDS.name))
        }
        return v
    }

    override fun onResume() {
        super.onResume()
        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long
        viewModel.loadAllFriends(personId)
    }

    private fun init(v: View) {
        recyclerView = v.findViewById<View>(R.id.rv_friend) as RecyclerView
        tvOverall = v.findViewById(R.id.tv_overall_you_owe_friends)
        cvAddFriend = v.findViewById(R.id.cv_add_friends)
    }

    fun setUpRecyclerView() {
        friendAdapter = FriendAdapter(this, allFriendsList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = friendAdapter
    }

    private fun getData() {
        lifecycleScope.launch {
            viewModel.allFriends.collect {
                val (friendList, youOverallOweOrOwed) = it
                if (friendList.isNotEmpty()) {
                    allFriendsList.clear()
                    allFriendsList.addAll(friendList)
                    overall(allFriendsList.size, youOverallOweOrOwed)
                    friendAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun overall(size: Int, overall: Double) {
        if (size == 0) {
            tvOverall.visible()
            tvOverall.text = getString(R.string.you_are_all_settled_up)
            tvOverall.setTextColor(tvOverall.context.resources.getColor(R.color.black))
        } else if (size > 1) {
            tvOverall.visible()

            val colorResId =
                if (overall == 0.0) R.color.black else if (overall < 0) R.color.primary_dark else R.color.green
            val absOverall = Math.abs(overall)

            tvOverall.text = if (overall == 0.0) {
                getString(R.string.you_are_settled_up_overall)
            } else if (overall < 0) {
                getString(R.string.you_owe_rs_overall, absOverall.formatNumber(2))
            } else {
                getString(R.string.you_are_owed_rs_overall, absOverall.formatNumber(2))
            }

            tvOverall.setTextColor(tvOverall.context.resources.getColor(colorResId))
        } else {
            tvOverall.gone()
        }
    }
}