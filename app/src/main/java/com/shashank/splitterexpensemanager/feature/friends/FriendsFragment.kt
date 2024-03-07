package com.shashank.splitterexpensemanager.feature.friends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.FRIEND_ID
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FriendsFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var tvOverall: TextView

    @Inject
    lateinit var sharedPref: SharedPref
    @Inject
    lateinit var actionProcessor: ActionProcessor
    private val viewModel: FriendsViewModel by viewModels()
    private var allFriendsList = mutableListOf<Pair<Person, Double>>()
    lateinit var friendAdapter: FriendAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_friends, container, false)
        val personId: Long = sharedPref.getValue(PERSON_ID, 0L) as Long

        init(v)
        setUprecyclerView()
        viewModel.loadAllFriends(personId)
        lifecycleScope.launch {
            viewModel.allFriends.collect {
                if (it.friendsHashMap.isNotEmpty()) {
                    allFriendsList.clear()
                    allFriendsList.addAll(it.friendsHashMap.toList())
                    overall(allFriendsList.size, it.overallOweOrOwed)
                    friendAdapter.notifyDataSetChanged()
                }
            }
        }
        return v
    }

    private fun init(v: View) {
        recyclerView = v.findViewById<View>(R.id.rv_friend) as RecyclerView
        tvOverall = v.findViewById(R.id.tv_overall_you_owe_friends)
    }

    fun setUprecyclerView() {
        friendAdapter = FriendAdapter(allFriendsList,object :FriendAdapter.OnItemClickListener{
            override fun onItemClick(pair: Pair<Person, Double>) {
                val id:Long=pair.first.id?:-1
                actionProcessor.process(ActionRequestSchema(
                    ActionType.Friends_DETAILS.name,
                    hashMapOf(
                        FRIEND_ID to id,
                    )))
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = friendAdapter
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