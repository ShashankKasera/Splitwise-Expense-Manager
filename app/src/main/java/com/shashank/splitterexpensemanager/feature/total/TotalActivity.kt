package com.shashank.splitterexpensemanager.feature.total

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TotalActivity : AppCompatActivity() {

    private val viewModel: TotalViewModel by viewModels()

    lateinit var tvTotalGroupSpending: TextView
    lateinit var tvTotalYouPaid: TextView
    lateinit var tvYourTotalShare: TextView

    @Inject
    lateinit var sharedPref: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total)

        init()
        totalGroupSpending()
        totalYouPaidFor()
        yourTotalShare()
    }

    private fun init() {
        val groupId: Long = intent.extras?.getLong(GROUP_ID) ?: 0
        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long

        tvTotalGroupSpending = findViewById(R.id.tv_total_group_spending_amount)
        tvTotalYouPaid = findViewById(R.id.tv_total_you_paid_amount)
        tvYourTotalShare = findViewById(R.id.tv_total_your_share_amount)
        viewModel.getTotalGroupSpendingExpenses(groupId)
        viewModel.getTotalYouPaidForExpenses(groupId, personId)
        viewModel.getYourTotalShare(groupId)
    }

    private fun totalGroupSpending() {
        lifecycleScope.launch {
            viewModel.totalGroupSpending.collect {
                tvTotalGroupSpending.text = it.formatNumber(2)
            }
        }
    }

    private fun totalYouPaidFor() {
        lifecycleScope.launch {
            viewModel.totalYouPaidFor.collect {
                tvTotalYouPaid.text = it.formatNumber(2)
            }
        }
    }

    private fun yourTotalShare() {
        lifecycleScope.launch {
            viewModel.yourTotalShare.collect {
                tvYourTotalShare.text = it.formatNumber(2)
            }
        }
    }
}