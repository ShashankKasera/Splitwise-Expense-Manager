package com.shashank.splitterexpensemanager.feature.groupdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
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
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPerson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GroupDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var actionProcessor: ActionProcessor
    lateinit var recyclerView: RecyclerView
    lateinit var tvGroupName: TextView
    lateinit var tvAddExpenses: TextView
    lateinit var llAddGroupMember: LinearLayout
    private val viewModel: GroupDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_details)
        var groupId: Long = intent.extras?.getLong(GROUP_ID) ?: 0
        init()


        navigationForAddFriends(groupId)
        navigationForGroupMember(groupId)
        getData(groupId)
        navigationForAddExpenses(groupId)
    }

    private fun init() {
        recyclerView = findViewById(R.id.rv_group_activity)
        tvGroupName = findViewById(R.id.tv_group_Name_in_detail)
        tvAddExpenses = findViewById(R.id.tv_add_expenses)
        llAddGroupMember = findViewById(R.id.ll_group_member)
    }

    private fun navigationForAddFriends(groupId: Long) {
        llAddGroupMember.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.ADD_FRIENDS.name,
                    hashMapOf(
                        GROUP_ID to (groupId)
                    )
                )
            )
        }
    }

    private fun navigationForGroupMember(groupId: Long) {
        tvGroupName.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.GROUP_MEMBER.name,
                    hashMapOf(
                        GROUP_ID to (groupId)
                    )
                )
            )
        }
    }

    private fun navigationForAddExpenses(groupId: Long) {
        tvAddExpenses.setOnClickListener {
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

    private fun getData(groupId: Long) {
        lifecycleScope.launch {
            viewModel.loadAllExpensesLiveData(groupId)
            viewModel.expenses.collect { expenses ->
                if (!expenses.isEmpty()) {
                    llAddGroupMember.gone()
                    recyclerView.visible()
                    recyclerViewSetUp(personId, expenses)
                }
            }
        }
        lifecycleScope.launch {
            viewModel.getGroup(groupId)
            viewModel.group.collect {
                if (it != null) tvGroupName.text = it.groupName
            }
        }
    }

    private fun recyclerViewSetUp(
        personId: Long,
        expensesList: List<ExpenseWithCategoryAndPerson?>
    ) {
        var expensesAdapter = ExpensesAdapter(personId, expensesList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = expensesAdapter
    }
}