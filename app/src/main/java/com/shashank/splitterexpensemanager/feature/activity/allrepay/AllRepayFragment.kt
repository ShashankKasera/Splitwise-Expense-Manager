package com.shashank.splitterexpensemanager.feature.activity.allrepay

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
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.feature.activity.ActivityViewModel
import com.shashank.splitterexpensemanager.model.RepayWithPersonAndGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AllRepayFragment : Fragment() {
    lateinit var rvRepay: RecyclerView
    lateinit var allRepayAdapter: AllRepayAdapter

    @Inject
    lateinit var actionProcessor: ActionProcessor

    private var repayList = mutableListOf<RepayWithPersonAndGroup>()

    @Inject
    lateinit var sharedPref: SharedPref
    private val viewModel: ActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_all_repay, container, false)

        init(v)
        setupRecyclerView()
        getData()
        return v
    }

    private fun init(v: View) {
        rvRepay = v.findViewById(R.id.rv_all_repay)
        viewModel.allRepay()
    }

    private fun setupRecyclerView() {
        allRepayAdapter = AllRepayAdapter(repayList)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.setReverseLayout(true)
        layoutManager.setStackFromEnd(true)
        rvRepay.layoutManager = layoutManager
        rvRepay.adapter = allRepayAdapter
    }

    private fun getData() {
        lifecycleScope.launch {
            viewModel.allRepay.collect {
                repayList.clear()
                repayList.addAll(it)
                allRepayAdapter.notifyDataSetChanged()
                rvRepay.layoutManager?.scrollToPosition(repayList.size - 1)
            }
        }
    }
}