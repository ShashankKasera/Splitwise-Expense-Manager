package com.shashank.splitterexpensemanager.feature.groupdetails.repay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.feature.groupdetails.GroupDetailViewModel
import com.shashank.splitterexpensemanager.model.RepayWithPerson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RepayFragment : Fragment() {

    lateinit var rvRepay: RecyclerView
    lateinit var repayAdapter: RepayAdapter

    @Inject
    lateinit var actionProcessor: ActionProcessor

    private var repayList = mutableListOf<RepayWithPerson>()

    private val viewModel: GroupDetailViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_repay, container, false)

        val groupId: Long = viewModel.groupId
        val personId = viewModel.personId

        init(v, groupId, personId)
        setupRecyclerView()
        getData()
        return v
    }

    private fun init(v: View, groupId: Long, personId: Long) {
        rvRepay = v.findViewById(R.id.rv_group_repay)
        viewModel.groupDetails(groupId, personId)
    }

    private fun setupRecyclerView() {
        repayAdapter = RepayAdapter(repayList)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.setReverseLayout(true)
        layoutManager.setStackFromEnd(true)
        rvRepay.layoutManager = layoutManager
        rvRepay.adapter = repayAdapter
    }

    private fun getData() {
        lifecycleScope.launch {
            viewModel.groupDetails.collect {
                repayList.clear()
                repayList.addAll(it.repay)
                repayAdapter.notifyDataSetChanged()
                rvRepay.layoutManager?.scrollToPosition(repayList.size - 1)
            }
        }
    }
}