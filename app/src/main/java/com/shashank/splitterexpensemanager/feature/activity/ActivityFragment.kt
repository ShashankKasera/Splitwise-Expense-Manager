package com.shashank.splitterexpensemanager.feature.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPersonAndGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ActivityFragment : Fragment() {
    @Inject
    lateinit var sharedPref: SharedPref
    lateinit var recyclerView: RecyclerView
    lateinit var activityAdapter: ActivityAdapter
    private var activityList = mutableListOf<ExpenseWithCategoryAndPersonAndGroup>()
    private val viewModel: ActivityViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_activity, container, false)
        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long
        init(v)
        setUpRecyclerView(personId)

        viewModel.allActivity()
        lifecycleScope.launch {
            viewModel.allActivity.collect {
                if (it.isNotEmpty()) {
                    activityList.clear()
                    activityList.addAll(it)
                    activityAdapter.notifyDataSetChanged()
                }
            }
        }
        return v
    }

    private fun init(v: View) {
        recyclerView = v.findViewById<View>(R.id.rv_activity) as RecyclerView
    }

    private fun setUpRecyclerView(personId: Long) {
        activityAdapter = ActivityAdapter(personId, activityList)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.setReverseLayout(true)
        layoutManager.setStackFromEnd(true)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = activityAdapter
    }
}