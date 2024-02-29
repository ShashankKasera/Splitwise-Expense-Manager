package com.shashank.splitterexpensemanager.feature.expensesdetails

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.EXPENSES_ID
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.model.OweOrOwedWithPerson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ExpensesDetailsActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var tvAmount: TextView
    lateinit var tvPaidBy: TextView
    lateinit var tvTime: TextView
    lateinit var tvDate: TextView
    lateinit var ivDelete: ImageView
    lateinit var tvdescription: TextView
    private var oweOwedList = mutableListOf<OweOrOwedWithPerson>()
    lateinit var splitAmountAdapter: SplitAmountAdapter
    private val viewModel: ExpensesDetailsViewModel by viewModels()

    @Inject
    lateinit var sharedPref: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses_details)
        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long
        val expensesId: Long = intent.extras?.getLong(EXPENSES_ID) ?: -1

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
    }

    private fun init() {
        recyclerView = findViewById(R.id.rv_group_expense)
        tvAmount = findViewById(R.id.tv_paid_amount_expenses_details)
        tvPaidBy = findViewById(R.id.tv_paid_by)
        tvDate = findViewById(R.id.tv_date)
        tvTime = findViewById(R.id.tv_time)
        tvdescription = findViewById(R.id.tv_description)
        ivDelete = findViewById(R.id.iv_delete_expenses)
    }

    private fun setUpRecyclerView(personId: Long) {
        splitAmountAdapter = SplitAmountAdapter(this, personId, oweOwedList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = splitAmountAdapter
    }
}