package com.shashank.splitterexpensemanager.feature.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.core.ui.FilterAdapter
import com.shashank.splitterexpensemanager.model.GroupAndOweOrOwed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GroupFragment : Fragment() {

    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var actionProcessor: ActionProcessor
    lateinit var recyclerView: RecyclerView
    lateinit var toolbar: Toolbar
    lateinit var groupAdapter: GroupAdapter
    private var allGroupsList = mutableListOf<GroupAndOweOrOwed>()
    private var filterList = ArrayList<String>()
    lateinit var ivFilter: ImageView
    lateinit var dialog: BottomSheetDialog
    lateinit var filterRecyclerView: RecyclerView
    lateinit var filterAdapter: FilterAdapter
    private var selectPosition: Int = 0
    lateinit var tvFilter: TextView
    lateinit var ivManChillingOut: ImageView
    lateinit var tvNoOneToSeeHare: TextView
    lateinit var llEmptyList: LinearLayout
    lateinit var llFirstTime: LinearLayout
    lateinit var llAddGroups: LinearLayout
    lateinit var ivAddGroup: ImageView
    lateinit var tvOverall: TextView
    lateinit var tvClearFilter: TextView
    private val viewModel: GroupViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_group, container, false)
        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long
        init(v)
        setUpRecyclerView()
        viewModel.getGroupSize()
        lifecycleScope.launch {
            viewModel.groupSize.collect {
                if (it == 0) {
                    llFirstTime.visible()
                    ivFilter.gone()
                    tvOverall.gone()
                } else if (it != -1) {
                    llFirstTime.gone()
                    ivFilter.visible()
                    tvOverall.visible()
                    viewModel.getAllGroup(personId)
                    getAllGroups()
                    filter(personId)
                    clearFilter(personId)
                }
            }
        }
        llAddGroups.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.ADD_GROUP.name,
                )
            )
        }

        ivAddGroup.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.ADD_GROUP.name,
                )
            )
        }
        return v
    }

    override fun onResume() {
        super.onResume()
        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long
        viewModel.getAllGroup(personId)
    }

    private fun init(v: View) {
        recyclerView = v.findViewById(R.id.rv_group)
        toolbar = v.findViewById(R.id.tb_group)
        ivFilter = v.findViewById(R.id.iv_filter_group)
        tvOverall = v.findViewById(R.id.tv_overall_you_owe_group)
        llAddGroups = v.findViewById(R.id.ll_add_groups)
        tvFilter = v.findViewById(R.id.tv_filter_group)
        ivManChillingOut = v.findViewById(R.id.iv_man_chilling_out_group)
        tvNoOneToSeeHare = v.findViewById(R.id.tv_no_one_to_see_hare_groups)
        llEmptyList = v.findViewById(R.id.ll_empty_list_group)
        tvClearFilter = v.findViewById(R.id.tv_clear_filter_groups)
        llFirstTime = v.findViewById(R.id.ll_first_time_group)
        ivAddGroup = v.findViewById(R.id.iv_add_group)
        filterList.add(getString(R.string.all_groups))
        filterList.add(getString(R.string.outstanding_balance))
        filterList.add(getString(R.string.group_you_owe))
        filterList.add(getString(R.string.group_that_owe_you))

        toolbar.setTitle(getString(R.string.dashboard))
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
                                    getString(R.string.showing_groups_with_outstanding_balances)
                                viewModel.getAllGroup(personId)
                                getOutstandingBalances()
                            }

                            getString(R.string.group_you_owe) -> {
                                tvFilter.text = getString(R.string.showing_only_groups_you_owe)
                                viewModel.getAllGroupYouOwe(personId)
                                getGroupsYouOwe()
                            }

                            getString(R.string.group_that_owe_you) -> {
                                tvFilter.text = getString(R.string.showing_only_groups_that_owe_you)
                                viewModel.getAllGroupWhoOweYou(personId)
                                getGroupsWhoOewYou()
                            }

                            else -> {
                                tvFilter.gone()
                                tvFilter.text = getString(R.string.all_groups)
                                viewModel.getAllGroup(personId)
                                getAllGroups()
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

    private fun setUpRecyclerView() {
        groupAdapter = GroupAdapter(
            this,
            allGroupsList,
            object : GroupAdapter.OnItemClickListener {
                override fun onItemClick(groupId: Long) {
                    actionProcessor.process(
                        ActionRequestSchema(
                            ActionType.GROUP_DETAILS.name,
                            hashMapOf(
                                GROUP_ID to groupId,
                            )
                        )
                    )
                }
            }
        )

        val layoutManager = LinearLayoutManager(context)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = groupAdapter
    }

    private fun getAllGroups() {
        lifecycleScope.launch {
            viewModel.allGroup.collect {
                val (groupList, youOverallOweOrOwed) = it
                if (youOverallOweOrOwed == -1.0) {
                    llEmptyList.gone()

                    allGroupsListClear()
                } else {
                    if (groupList.isNotEmpty()) {
                        llEmptyList.gone()
                        allGroupsListAddAll(groupList)
                        overall(allGroupsList.size, youOverallOweOrOwed)
                    } else {
                        allGroupsListClear()
                        llEmptyList.visible()
                    }
                }
            }
        }
    }

    private fun getOutstandingBalances() {
        lifecycleScope.launch {
            viewModel.allGroup.collect {
                val (groupList, youOverallOweOrOwed) = it
                if (youOverallOweOrOwed == -1.0) {
                    llEmptyList.gone()
                    allGroupsListClear()
                    setOverallFroOutstandingBalances()
                } else {
                    if (groupList.isNotEmpty()) {
                        allGroupsList.clear()
                        allGroupsList.addAll(groupList)
                        allGroupsList.removeIf { it.overall == 0.0 }
                        groupAdapter.notifyDataSetChanged()

                        if (allGroupsList.isNotEmpty()) {
                            llEmptyList.gone()
                            tvFilter.visible()
                            overall(allGroupsList.size, youOverallOweOrOwed)
                        } else {
                            llEmptyList.visible()
                            tvFilter.gone()
                            setOverallFroOutstandingBalances()
                        }
                    } else {
                        allGroupsListClear()
                        setOverallFroOutstandingBalances()
                        llEmptyList.visible()
                        tvFilter.gone()
                    }
                }
            }
        }
    }

    private fun getGroupsYouOwe() {
        lifecycleScope.launch {
            viewModel.allGroupYouOwe.collect {
                val (groupList, youOverallOweOrOwed) = it
                if (youOverallOweOrOwed == -1.0) {
                    llEmptyList.gone()
                    allGroupsListClear()
                    setOverallFroGroupsYouOwe()
                } else {
                    if (groupList.isNotEmpty()) {
                        allGroupsListAddAll(groupList)
                        if (allGroupsList.isNotEmpty()) {
                            llEmptyList.gone()
                            tvFilter.visible()
                            if (allGroupsList.any { youOverallOweOrOwed == 0.0 }) {
                                setOverallFroGroupsYouOwe()
                            } else {
                                overall(allGroupsList.size, youOverallOweOrOwed)
                            }
                        } else {
                            tvFilter.gone()
                            llEmptyList.visible()
                        }
                    } else {
                        setOverallFroGroupsYouOwe()
                        tvFilter.gone()
                        llEmptyList.visible()
                        allGroupsListClear()
                    }
                }
            }
        }
    }

    private fun getGroupsWhoOewYou() {
        lifecycleScope.launch {
            viewModel.allGroupWhoOweYou.collect {
                val (groupList, youOverallOweOrOwed) = it
                if (youOverallOweOrOwed == -1.0) {
                    llEmptyList.gone()
                    allGroupsListClear()
                    setOverallFroGroupsWhoOewYou()
                } else {
                    if (groupList.isNotEmpty()) {
                        allGroupsListAddAll(groupList)
                        if (allGroupsList.isNotEmpty()) {
                            llEmptyList.gone()
                            tvFilter.visible()
                            if (allGroupsList.any { it.overall == 0.0 }) {
                                setOverallFroGroupsWhoOewYou()
                            } else {
                                overall(allGroupsList.size, youOverallOweOrOwed)
                            }
                        } else {
                            llEmptyList.visible()
                            tvFilter.gone()
                        }
                    } else {
                        setOverallFroGroupsWhoOewYou()
                        llEmptyList.visible()
                        tvFilter.gone()
                        allGroupsListClear()
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

    private fun setOverallFroGroupsWhoOewYou() {
        tvOverall.text = getString(R.string.no_group_owe_you)
        tvOverall.setTextColor(tvOverall.context.resources.getColor(R.color.dark_grey))
    }

    private fun setOverallFroGroupsYouOwe() {
        tvOverall.text = getString(R.string.you_do_not_owe_any_group)
        tvOverall.setTextColor(tvOverall.context.resources.getColor(R.color.dark_grey))
    }

    private fun setOverallFroOutstandingBalances() {
        tvOverall.text = getString(R.string.you_are_all_settled_up)
        tvOverall.setTextColor(tvOverall.context.resources.getColor(R.color.dark_grey))
    }

    private fun allGroupsListClear() {
        allGroupsList.clear()
        groupAdapter.notifyDataSetChanged()
    }

    private fun allGroupsListAddAll(groupList: List<GroupAndOweOrOwed>) {
        allGroupsList.clear()
        allGroupsList.addAll(groupList)
        groupAdapter.notifyDataSetChanged()
    }

    private fun clearFilter(personId: Long) {
        tvClearFilter.setOnClickListener {
            viewModel.getAllGroup(personId)
            getAllGroups()
        }
    }
}