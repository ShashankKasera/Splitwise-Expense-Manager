package com.shashank.splitterexpensemanager.feature.activity.allexpenses

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.feature.activity.ActivityViewModel
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPersonAndGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AllExpensesFragment : Fragment() {


    lateinit var rvExpenses: RecyclerView
    lateinit var expensesAdapter: AllExpensesAdapter

    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var actionProcessor: ActionProcessor

    private var expensesList = mutableListOf<ExpenseWithCategoryAndPersonAndGroup>()

    private val viewModel: ActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_all_expenses, container, false)
        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long

        init(v)
        setupRecyclerView(personId)
        getData()
        return v
    }

    private fun init(v: View) {
        rvExpenses = v.findViewById(R.id.rv_all_expenses)
        viewModel.allExpenses()
    }

    private fun setupRecyclerView(personId: Long) {
        expensesAdapter = AllExpensesAdapter(actionProcessor, personId, expensesList)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.setReverseLayout(true)
        layoutManager.setStackFromEnd(true)
        rvExpenses.layoutManager = layoutManager
        rvExpenses.adapter = expensesAdapter
    }

    private fun getData() {
        lifecycleScope.launch {
            viewModel.allExpenses.collect {
                expensesList.clear()
                expensesList.addAll(it)
                Log.i("whbja", "getData  AllExpensesFragment : $it")
                expensesAdapter.notifyDataSetChanged()
                rvExpenses.layoutManager?.scrollToPosition(expensesList.size - 1)
            }
        }
    }
}