package com.shashank.splitterexpensemanager.feature.groupdetails.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.feature.groupdetails.GroupDetailViewModel
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPerson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ExpensesFragment : Fragment() {
    lateinit var rvExpenses: RecyclerView
    lateinit var expensesAdapter: ExpensesAdapter

    @Inject
    lateinit var actionProcessor: ActionProcessor
    lateinit var cvAddExpenses: CardView
    private var expensesList = mutableListOf<ExpenseWithCategoryAndPerson>()

    private val viewModel: GroupDetailViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_expenses, container, false)
        val groupId: Long = viewModel.groupId
        val personId = viewModel.personId
        init(v, groupId, personId)
        setupRecyclerView(personId)
        navigationForAddExpenses(groupId)
        getData()
        return v
    }

    private fun init(v: View, groupId: Long, personId: Long) {
        rvExpenses = v.findViewById(R.id.rv_group_expense)
        cvAddExpenses = v.findViewById(R.id.cv_add_expenses)
        viewModel.groupDetails(groupId, personId)
    }

    private fun setupRecyclerView(personId: Long) {
        expensesAdapter = ExpensesAdapter(actionProcessor, personId, expensesList)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.setReverseLayout(true)
        layoutManager.setStackFromEnd(true)
        rvExpenses.layoutManager = layoutManager
        rvExpenses.adapter = expensesAdapter
    }

    private fun getData() {
        lifecycleScope.launch {
            viewModel.groupDetails.collect { groupDetails ->
                val groupMemberCount = groupDetails.groupMember.size
                if (groupMemberCount > 1) {
                    cvAddExpenses.visible()
                } else {
                    cvAddExpenses.gone()
                }
                expensesList.clear()
                expensesList.addAll(groupDetails.expenses)
                expensesAdapter.notifyDataSetChanged()
                rvExpenses.layoutManager?.scrollToPosition(expensesList.size - 1)
            }
        }
    }

    private fun navigationForAddExpenses(groupId: Long) {
        cvAddExpenses.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.ADD_EXPENSES.name,
                    hashMapOf(
                        GROUP_ID to (groupId),
                    )
                )
            )
        }
    }
}