package com.shashank.splitterexpensemanager.feature.expensesdetails

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.EXPENSES_ID
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.UPDATE_EXPENSES
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.model.OweOrOwedWithPerson
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ExpensesDetailsActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var tvAmount: TextView
    lateinit var civCategory: CircleImageView
    lateinit var tvPaidBy: TextView
    lateinit var tvTime: TextView
    lateinit var tvDate: TextView
    lateinit var ivDelete: ImageView
    lateinit var ivUpdate: ImageView
    lateinit var tvdescription: TextView
    lateinit var ivBack: ImageView
    private var oweOwedList = mutableListOf<OweOrOwedWithPerson>()
    lateinit var splitAmountAdapter: SplitAmountAdapter
    private val viewModel: ExpensesDetailsViewModel by viewModels()

    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var actionProcessor: ActionProcessor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses_details)
        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long
        val expensesId: Long = intent.extras?.getLong(EXPENSES_ID) ?: -1
        val groupId: Long = intent.extras?.getLong(GROUP_ID) ?: -1

        init()
        setUpRecyclerView(personId)

        viewModel.loadExpensesDetails(expensesId)
        lifecycleScope.launch {
            viewModel.expenses.collect {
                if (it != null) {
                    tvAmount.text =
                        getString(
                            R.string.paid_amount_expensese_details,
                            (it.expense.amount).formatNumber(2)
                        )
                    Glide.with(this@ExpensesDetailsActivity).load(it.category.categoryImage)
                        .into(civCategory)
                    tvPaidBy.text = getString(R.string.paid_by_expensese_details, it.expense.name)
                    tvDate.text = getString(R.string.date_expensese_details, it.expense.date)
                    tvTime.text = getString(R.string.time_expensese_details, it.expense.time)
                    tvdescription.text =
                        getString(R.string.description_expensese_details, it.expense.description)
                }
            }
        }
        viewModel.loadOweOwedByExpensesId(expensesId)
        lifecycleScope.launch {
            viewModel.oweOwed.collect {
                if (it.size > 0) {
                    oweOwedList.clear()
                    oweOwedList.addAll(it)
                    splitAmountAdapter.notifyDataSetChanged()
                }
            }
        }

        ivDelete.setOnClickListener {
            viewModel.deleteExpenses(expensesId)
            finish()
        }

        ivUpdate.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.ADD_EXPENSES.name,
                    hashMapOf(
                        UPDATE_EXPENSES to true,
                        EXPENSES_ID to expensesId,
                        GROUP_ID to groupId,
                    )
                )
            )
        }
    }

    private fun init() {
        recyclerView = findViewById(R.id.rv_group_expense)
        tvAmount = findViewById(R.id.tv_paid_amount_expenses_details)
        civCategory = findViewById(R.id.civ_category_expenses_details)
        tvPaidBy = findViewById(R.id.tv_paid_by)
        tvDate = findViewById(R.id.tv_date)
        tvTime = findViewById(R.id.tv_time)
        tvdescription = findViewById(R.id.tv_description)
        ivDelete = findViewById(R.id.iv_delete_expenses)
        ivUpdate = findViewById(R.id.iv_edit_expenses)

        ivBack = findViewById(R.id.iv_back_expenses_details)

        ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setUpRecyclerView(personId: Long) {
        splitAmountAdapter = SplitAmountAdapter(this, personId, oweOwedList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = splitAmountAdapter
    }
}