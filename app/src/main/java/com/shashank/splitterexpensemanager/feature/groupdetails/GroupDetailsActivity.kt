package com.shashank.splitterexpensemanager.feature.groupdetails

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPerson
import com.shashank.splitterexpensemanager.model.GroupDetails
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GroupDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var actionProcessor: ActionProcessor
    lateinit var rvExpenses: RecyclerView
    lateinit var rvOweOwed: RecyclerView
    lateinit var tvGroupName: TextView
    lateinit var cvAddExpenses: CardView
    lateinit var tvOverallOweOrOwed: TextView
    lateinit var tvNoExpenses: TextView
    lateinit var tvYouAreTheOnlyOneHere: TextView
    lateinit var clOweOwed: ConstraintLayout
    lateinit var expensesAdapter: ExpensesAdapter
    lateinit var oweOwedAdapter: OweOwedAdapter
    lateinit var tvOweOrOwedOther: TextView
    lateinit var llAddGroupMember: LinearLayout
    private var expensesList = mutableListOf<ExpenseWithCategoryAndPerson>()
    private var oweOwedList = mutableListOf<Pair<Person, Double>>()
    private val viewModel: GroupDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_details)
        var groupId: Long = intent.extras?.getLong(GROUP_ID) ?: 0
        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long
        init()
        recyclerViewSetUp(personId)
        navigationForAddFriends(groupId)
        navigationForGroupMember(groupId)
        getData()
        navigationForAddExpenses(groupId)
        getData()
    }

    private fun init() {
        rvExpenses = findViewById(R.id.rv_group_expense)
        rvOweOwed = findViewById(R.id.rv_owe_owed)
        tvNoExpenses = findViewById(R.id.tv_no_expenses_here_yet)
        tvGroupName = findViewById(R.id.tv_group_Name_in_detail)
        cvAddExpenses = findViewById(R.id.cv_add_expenses)
        tvOverallOweOrOwed = findViewById(R.id.tv_overall_owe)
        llAddGroupMember = findViewById(R.id.ll_group_member)
        tvOweOrOwedOther = findViewById(R.id.tv_other_member)
        clOweOwed = findViewById(R.id.cl_amount)
        tvYouAreTheOnlyOneHere = findViewById(R.id.tv_you_re_the_only_one_here)

        val groupId: Long = intent.extras?.getLong(GROUP_ID) ?: 0
        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long
        viewModel.groupDetails(groupId, personId)
    }

    override fun onRestart() {
        super.onRestart()
        val groupId: Long = intent.extras?.getLong(GROUP_ID) ?: 0
        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long
        viewModel.groupDetails(groupId, personId)
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

    private fun getData() {
        lifecycleScope.launch {
            viewModel.groupDetails.collect {
                if (it.groupMember.size > 1) {
                    tvYouAreTheOnlyOneHere.gone()
                    cvAddExpenses.visible()
                } else {
                    tvYouAreTheOnlyOneHere.visible()
                    cvAddExpenses.gone()
                }
                if (!it.expenses.isEmpty()) {
                    llAddGroupMember.gone()
                    tvYouAreTheOnlyOneHere.gone()
                    rvExpenses.visible()
                    clOweOwed.visible()
                    tvNoExpenses.gone()
                    expensesList.clear()
                    expensesList.addAll(it.expenses)
                    expensesAdapter.notifyDataSetChanged()
                    rvExpenses.layoutManager?.scrollToPosition(expensesList.size - 1)
                } else {
                    rvExpenses.gone()
                    clOweOwed.gone()
                    llAddGroupMember.visible()
                    tvNoExpenses.visible()
                }
                if (it.group != null) tvGroupName.text = it.group.groupName

                if (it.hashMap.isNotEmpty()) {
                    oweOwedList.clear()
                    oweOwedList.addAll(it.hashMap.toList())
                    oweOwedList.removeIf { it.second == 0.0 }
                    oweOwedAdapter.notifyDataSetChanged()
                    overall(it, oweOwedList.size)
                    if (oweOwedList.size > 2) {
                        tvOweOrOwedOther.visible()
                        tvOweOrOwedOther.text =
                            getString(R.string.plus_other_balance, (oweOwedList.size - 2))
                    } else {
                        tvOweOrOwedOther.gone()
                    }
                }
            }
        }
    }

    private fun overall(it: GroupDetails, size: Int) {
        if (size == 0) {
            tvOverallOweOrOwed.visible()
            tvOverallOweOrOwed.text = getString(R.string.you_are_all_settled_up_in_this_group)
            tvOverallOweOrOwed.setTextColor(tvOverallOweOrOwed.context.resources.getColor(R.color.black))
        } else if (size > 1) {
            tvOverallOweOrOwed.visible()
            var overall = 0.0
            var oweAmount = 0.0
            var owedAmount = 0.0

            it.hashMap.forEach { (_, value) ->
                if (value > 0) owedAmount += value
                if (value < 0) oweAmount += value
            }

            overall = oweAmount + owedAmount

            val colorResId =
                if (overall == 0.0) R.color.black else if (overall < 0) R.color.primary_dark else R.color.green
            val absOverall = Math.abs(overall)

            tvOverallOweOrOwed.text = if (overall == 0.0) {
                getString(R.string.you_are_settled_up_overall)
            } else if (overall < 0) {
                getString(R.string.you_owe_rs_overall, absOverall.formatNumber(2))
            } else {
                getString(R.string.you_are_owed_rs_overall, absOverall.formatNumber(2))
            }

            tvOverallOweOrOwed.setTextColor(tvOverallOweOrOwed.context.resources.getColor(colorResId))
        } else {
            tvOverallOweOrOwed.gone()
        }
    }

    private fun recyclerViewSetUp(
        personId: Long,
    ) {
        oweOwedAdapter = OweOwedAdapter(this, oweOwedList)
        rvOweOwed.layoutManager = LinearLayoutManager(this)
        rvOweOwed.adapter = oweOwedAdapter

        expensesAdapter = ExpensesAdapter(actionProcessor, personId, expensesList)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.setReverseLayout(true)
        layoutManager.setStackFromEnd(true)
        rvExpenses.layoutManager = layoutManager
        rvExpenses.adapter = expensesAdapter
    }
}