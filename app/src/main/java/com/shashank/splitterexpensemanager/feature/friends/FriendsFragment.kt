package com.shashank.splitterexpensemanager.feature.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.FRIEND_ID
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.core.ui.FilterAdapter
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
    lateinit var ivManChillingOut: ImageView
    lateinit var tvNoOneToSeeHare: TextView
    lateinit var llEmptyList: LinearLayout
    lateinit var llFirstTime: LinearLayout
    lateinit var tvOverall: TextView
    lateinit var tvClearFilter: TextView
    lateinit var ivFilter: ImageView
    lateinit var lladdFriends: LinearLayout
    lateinit var friendAdapter: FriendAdapter
    private val viewModel: FriendsViewModel by viewModels()
    private var allFriendsList = mutableListOf<Friends>()
    lateinit var dialog: BottomSheetDialog
    lateinit var filterRecyclerView: RecyclerView
    private var filterList = ArrayList<String>()
    lateinit var filterAdapter: FilterAdapter
    lateinit var tvFilter: TextView
    private var selectPosition: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_friends, container, false)
        val personId: Long = sharedPref.getValue(PERSON_ID, 0L) as Long
        init(v)

        setUpRecyclerView()

        viewModel.getAllPersonExcept(personId)
        lifecycleScope.launch {
            viewModel.allPersonExcept.collect {
                if (it == 0) {
                    llFirstTime.visible()
                } else if (it != -1) {
                    llFirstTime.gone()

                    viewModel.loadAllFriends(personId)
                    getData()
                    filter(personId)
                    clearFilter(personId)
                }
            }
        }

        lladdFriends.setOnClickListener {
            actionProcessor.process(ActionRequestSchema(ActionType.CREATE_FRIENDS.name))
        }
        return v
    }

    private fun clearFilter(personId: Long) {
        tvClearFilter.setOnClickListener {
            viewModel.loadAllFriends(personId)
            getData()
        }
    }

    override fun onResume() {
        super.onResume()
        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long
        viewModel.getAllPersonExcept(personId)
    }

    private fun init(v: View) {
        recyclerView = v.findViewById<View>(R.id.rv_friend) as RecyclerView
        tvOverall = v.findViewById(R.id.tv_overall_you_owe_friends)
        ivFilter = v.findViewById(R.id.iv_filter_friend)
        lladdFriends = v.findViewById(R.id.ll_add_friends)
        tvFilter = v.findViewById(R.id.tv_filter_friends)
        ivManChillingOut = v.findViewById(R.id.iv_man_chilling_out_friend)
        tvNoOneToSeeHare = v.findViewById(R.id.tv_no_one_to_see_hare_friends)
        llEmptyList = v.findViewById(R.id.ll_empty_list_friend)
        tvClearFilter = v.findViewById(R.id.tv_clear_filter_friends)
        llFirstTime = v.findViewById(R.id.ll_first_time_friend)

        filterList.add(getString(R.string.all_friends))
        filterList.add(getString(R.string.outstanding_balance))
        filterList.add(getString(R.string.friends_you_owe))
        filterList.add(getString(R.string.friends_who_owe_you))
    }

    fun setUpRecyclerView() {
        friendAdapter =
            FriendAdapter(
                this, allFriendsList,
                object : FriendAdapter.OnItemClickListener {
                    override fun onItemClick(friend: Person) {
                        val id: Long = friend.id ?: -1
                        actionProcessor.process(
                            ActionRequestSchema(
                                ActionType.FRIENDS_DETAILS.name,
                                hashMapOf(
                                    FRIEND_ID to id,
                                )
                            )
                        )
                    }
                }
            )
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = friendAdapter
    }


    private fun getData() {
        lifecycleScope.launch {
            viewModel.allFriends.collect {
                val (friendList, youOverallOweOrOwed) = it
                if (youOverallOweOrOwed == -1.0) {
                    llEmptyList.gone()

                    allFriendsListClear()
                } else {
                    if (friendList.isNotEmpty()) {
                        llEmptyList.gone()
                        allFriendsListAddAll(friendList)
                        overall(allFriendsList.size, youOverallOweOrOwed)
                    } else {
                        allFriendsListClear()
                        llEmptyList.visible()
                    }
                }
            }
        }
    }

    private fun getOutstandingBalances() {
        lifecycleScope.launch {
            viewModel.allFriends.collect {
                val (friendList, youOverallOweOrOwed) = it
                if (youOverallOweOrOwed == -1.0) {
                    llEmptyList.gone()
                    allFriendsListClear()
                    setOverallFroOutstandingBalances()
                } else {
                    if (friendList.isNotEmpty()) {
                        allFriendsList.clear()
                        allFriendsList.addAll(friendList)
                        allFriendsList.removeIf { it.overallOweOrOwed == 0.0 }
                        friendAdapter.notifyDataSetChanged()

                        if (allFriendsList.isNotEmpty()) {
                            llEmptyList.gone()
                            tvFilter.visible()
                            overall(allFriendsList.size, youOverallOweOrOwed)
                        } else {
                            llEmptyList.visible()
                            tvFilter.gone()
                            setOverallFroOutstandingBalances()
                        }
                    } else {
                        allFriendsListClear()
                        setOverallFroOutstandingBalances()
                        llEmptyList.visible()
                        tvFilter.gone()
                    }
                }
            }
        }
    }

    private fun getFriendsYouOwe() {
        lifecycleScope.launch {
            viewModel.allFriendsYouOwe.collect {
                val (friendList, youOverallOweOrOwed) = it
                if (youOverallOweOrOwed == -1.0) {
                    llEmptyList.gone()
                    allFriendsListClear()
                    setOverallFroFriendsYouOwe()
                } else {
                    if (friendList.isNotEmpty()) {
                        allFriendsListAddAll(friendList)
                        if (allFriendsList.isNotEmpty()) {
                            llEmptyList.gone()
                            tvFilter.visible()
                            if (allFriendsList.any { it.overallOweOrOwed == 0.0 }) {
                                setOverallFroFriendsYouOwe()
                            } else {
                                overall(allFriendsList.size, youOverallOweOrOwed)
                            }
                        } else {
                            tvFilter.gone()
                            llEmptyList.visible()
                        }
                    } else {
                        setOverallFroFriendsYouOwe()
                        tvFilter.gone()
                        llEmptyList.visible()
                        allFriendsListClear()
                    }
                }
            }
        }
    }

    private fun getFriendsWhoOewYou() {
        lifecycleScope.launch {
            viewModel.allFriendsWhoOweYou.collect {
                val (friendList, youOverallOweOrOwed) = it
                if (youOverallOweOrOwed == -1.0) {
                    llEmptyList.gone()
                    allFriendsListClear()
                    setOverallFroFriendsWhoOewYou()
                } else {
                    if (friendList.isNotEmpty()) {
                        allFriendsListAddAll(friendList)
                        if (allFriendsList.isNotEmpty()) {
                            llEmptyList.gone()
                            tvFilter.visible()
                            if (allFriendsList.any { it.overallOweOrOwed == 0.0 }) {
                                setOverallFroFriendsWhoOewYou()
                            } else {
                                overall(allFriendsList.size, youOverallOweOrOwed)
                            }
                        } else {
                            llEmptyList.visible()
                            tvFilter.gone()
                        }
                    } else {
                        setOverallFroFriendsWhoOewYou()
                        llEmptyList.visible()
                        tvFilter.gone()
                        allFriendsListClear()
                    }
                }
            }
        }
    }

    private fun overall(size: Int, overall: Double) {
        if (size == 0) {
            tvOverall.text = getString(R.string.you_are_all_settled_up)
            tvOverall.setTextColor(tvOverall.context.resources.getColor(R.color.dark_grey))
        } else if (size > 0) {
            val colorResId =
                if (overall == 0.0) R.color.dark_grey else if (overall < 0) R.color.primary_dark else R.color.green
            val absOverall = Math.abs(overall)
            tvOverall.text = if (overall == 0.0) {
                getString(R.string.you_are_settled_up_overall)
            } else if (overall < 0) {
                getString(R.string.you_owe_rs_overall, absOverall.formatNumber(2))
            } else {
                getString(R.string.you_are_owed_rs_overall, absOverall.formatNumber(2))
            }

            tvOverall.setTextColor(tvOverall.context.resources.getColor(colorResId))
        }
    }

    private fun filter(personId: Long) {
        ivFilter.setOnClickListener {
            context?.let {
                dialog = BottomSheetDialog(it, R.style.BottomSheetDialog)
            }
            val view = layoutInflater.inflate(R.layout.total_filter_bottom_sheet, null)
            filterRecyclerView = view.findViewById(R.id.rv_total_filter_bottom_sheet)
            dialog.setContentView(view)
            filterAdapter = FilterAdapter(
                selectPosition,
                filterList,
                object : FilterAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int, filter: String) {
                        selectPosition = position
                        filterAdapter.notifyDataSetChanged()
                        when (filter) {
                            getString(R.string.outstanding_balance) -> {
                                tvFilter.text =
                                    getString(R.string.showing_friends_with_outstanding_balances)
                                viewModel.loadAllFriends(personId)
                                getOutstandingBalances()
                            }

                            getString(R.string.friends_you_owe) -> {
                                tvFilter.text = getString(R.string.showing_only_friends_you_owe)
                                viewModel.loadAllFriendsYouOwe(personId)
                                getFriendsYouOwe()
                            }

                            getString(R.string.friends_who_owe_you) -> {
                                tvFilter.text = getString(R.string.showing_only_friends_who_owe_you)
                                viewModel.loadAllFriendsWhoOweYou(personId)
                                getFriendsWhoOewYou()
                            }

                            else -> {
                                tvFilter.gone()
                                tvFilter.text = getString(R.string.all_friends)
                                viewModel.loadAllFriends(personId)
                                getData()
                            }
                        }
                        dialog.dismiss()
                    }
                }
            )
            filterRecyclerView.layoutManager = LinearLayoutManager(context)
            filterRecyclerView.adapter = filterAdapter
            dialog.setCancelable(true)

            dialog.show()
        }
    }

    private fun setOverallFroFriendsWhoOewYou() {
        tvOverall.text = getString(R.string.you_do_not_owed_any_friends)
        tvOverall.setTextColor(tvOverall.context.resources.getColor(R.color.dark_grey))
    }

    private fun setOverallFroFriendsYouOwe() {
        tvOverall.text = getString(R.string.you_do_not_owe_any_friends)
        tvOverall.setTextColor(tvOverall.context.resources.getColor(R.color.dark_grey))
    }

    private fun setOverallFroOutstandingBalances() {
        tvOverall.text = getString(R.string.you_are_all_settled_up)
        tvOverall.setTextColor(tvOverall.context.resources.getColor(R.color.dark_grey))
    }

    private fun allFriendsListClear() {
        allFriendsList.clear()
        friendAdapter.notifyDataSetChanged()
    }

    private fun allFriendsListAddAll(friendList: List<Friends>) {
        allFriendsList.clear()
        allFriendsList.addAll(friendList)
        friendAdapter.notifyDataSetChanged()
    }
}