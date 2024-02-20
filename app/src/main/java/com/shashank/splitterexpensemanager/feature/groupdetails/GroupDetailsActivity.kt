package com.shashank.splitterexpensemanager.feature.groupdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.feature.addgroup.ui.model.GroupType
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPerson
import com.shashank.splitterexpensemanager.model.OweOrOwedWithPerson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GroupDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var actionProcessor: ActionProcessor
    lateinit var recyclerView: RecyclerView
    lateinit var tvGroupName: TextView
    lateinit var tvAddExpenses: TextView
    lateinit var tvOverallOweOrOwed: TextView
    lateinit var llAddGroupMember: LinearLayout

    @Inject
    lateinit var sharedPref: SharedPref
    private var amount=0.0
    private var owePersonList = ArrayList<OweOrOwedWithPerson>()
    private val viewModel: GroupDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_details)
        var groupId: Long = intent.extras?.getLong(GROUP_ID) ?: 0
        var personId: Long = sharedPref.getValue(PERSON_ID, 0L) as Long

        init(groupId, personId)
        navigationForAddFriends(groupId)
        navigationForGroupMember(groupId)
        navigationForAddExpenses(groupId)
        getData(groupId, personId)
    }

    private fun init(groupId: Long, personId: Long) {
        recyclerView = findViewById(R.id.rv_group_activity)
        tvGroupName = findViewById(R.id.tv_group_Name_in_detail)
        tvAddExpenses = findViewById(R.id.tv_add_expenses)
        tvOverallOweOrOwed = findViewById(R.id.tv_overall_owe)
        llAddGroupMember = findViewById(R.id.ll_group_member)
        viewModel.loadAllExpensesLiveData(personId,groupId)
        viewModel.loadAllOweByGroupId(groupId,personId)
        viewModel.loadAllOwedByGroupId(groupId,personId)
        lifecycleScope.launch {
            viewModel.amount.collect{
                amount=it?:0.0
            }
        }
        lifecycleScope.launch {
            viewModel.owe.collect{
//                Log.i("egb", "owe: ${it.size}")
            }
        }
        lifecycleScope.launch {
            viewModel.owed.collect{
                Log.i("egb", "owed: ${it.size}")
            }
        }
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

    private fun getData(groupId: Long, personId: Long) {
        lifecycleScope.launch {
            viewModel.oweOrOwed.collect {
                if(amount>0){
                    tvOverallOweOrOwed.setTextColor(
                        tvOverallOweOrOwed.context.getResources().getColor(R.color.green)
                    )
                }else{
                    tvOverallOweOrOwed.setTextColor(
                        tvOverallOweOrOwed.context.getResources().getColor(R.color.primary_dark)
                    )
                }
                tvOverallOweOrOwed.text=it.toString()
            }
        }
        lifecycleScope.launch {
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