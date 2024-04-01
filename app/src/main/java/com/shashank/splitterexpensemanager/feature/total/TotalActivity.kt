package com.shashank.splitterexpensemanager.feature.total

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.ui.FilterAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class TotalActivity : AppCompatActivity() {

    private val viewModel: TotalViewModel by viewModels()

    lateinit var dialog: BottomSheetDialog
    lateinit var tvTotalGroupSpending: TextView
    lateinit var tvTotalYouPaid: TextView
    lateinit var tvYourTotalShare: TextView
    lateinit var tvFilter: TextView
    lateinit var filter: CardView
    lateinit var toolbar: TextView
    lateinit var ivBack: ImageView
    lateinit var filterAdapter: FilterAdapter
    lateinit var filterRecyclerView: RecyclerView
    private var filterList = ArrayList<String>()
    private var selectPosition: Int = 0

    @Inject
    lateinit var sharedPref: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total)
        val groupId = intent.extras?.getLong(GROUP_ID) ?: 0
        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long

        init()

        allTime(groupId, personId)
        filterList.add(getString(R.string.all_time))
        filterList.add(getString(R.string.this_month))
        filterList.add(getString(R.string.last_month))

        filter(personId, groupId)
    }

    private fun filter(personId: Long, groupId: Long) {
        filter.setOnClickListener {
            dialog = BottomSheetDialog(this, R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.total_filter_bottom_sheet, null)
            filterRecyclerView = view.findViewById(R.id.rv_total_filter_bottom_sheet)
            dialog.setContentView(view)
            filterAdapter = FilterAdapter(
                selectPosition,
                filterList,
                object : FilterAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int, filter: String) {
                        selectPosition = position
                        filterAdapter.notifyDataSetChanged()
                        when (filter) {
                            getString(R.string.this_month) -> {
                                tvFilter.text = getString(R.string.this_month)
                                thisMonth(groupId, personId)
                            }

                            getString(R.string.last_month) -> {
                                tvFilter.text = getString(R.string.last_month)
                                lastMonth(groupId, personId)
                            }

                            else -> {
                                allTime(groupId, personId)
                                tvFilter.text = getString(R.string.all_time)
                            }
                        }
                        dialog.dismiss()
                    }
                }
            )
            filterRecyclerView.layoutManager = LinearLayoutManager(this)
            filterRecyclerView.adapter = filterAdapter
            dialog.setCancelable(true)

            dialog.show()
        }
    }

    private fun init() {
        tvTotalGroupSpending = findViewById(R.id.tv_total_group_spending_amount)
        tvTotalYouPaid = findViewById(R.id.tv_total_you_paid_amount)
        tvYourTotalShare = findViewById(R.id.tv_total_your_share_amount)
        tvFilter = findViewById(R.id.tv_total_filter)
        filter = findViewById(R.id.cv_total_filter)
        ivBack = findViewById(R.id.iv_tb_total)
        toolbar = findViewById(R.id.tv_tb_total)
        toolbar.text = getString(R.string.total)
        ivBack.setOnClickListener {
            finish()
        }
        tvFilter.text = getString(R.string.all_time)
    }

    private fun totalGroupSpending() {
        lifecycleScope.launch {
            viewModel.totalGroupSpending.collect {
                tvTotalGroupSpending.text = if (it != -1.0) it.formatNumber(2) else "0.0"
            }
        }
    }

    private fun totalGroupSpendingForThisMonth() {
        lifecycleScope.launch {
            viewModel.totalGroupSpendingForByMonthAndYear.collect {
                tvTotalGroupSpending.text = if (it != -1.0) it.formatNumber(2) else "0.0"
            }
        }
    }

    private fun totalYouPaidFor() {
        lifecycleScope.launch {
            viewModel.totalYouPaidFor.collect {
                tvTotalYouPaid.text = if (it != -1.0) it.formatNumber(2) else "0.0"
            }
        }
    }

    private fun totalYouPaidForForThisMonth() {
        lifecycleScope.launch {
            viewModel.totalYouPaidForForByMonthAndYear.collect {
                tvTotalYouPaid.text = if (it != -1.0) it.formatNumber(2) else "0.0"
            }
        }
    }

    private fun yourTotalShare() {
        lifecycleScope.launch {
            viewModel.yourTotalShare.collect {
                tvYourTotalShare.text = if (it != -1.0) it.formatNumber(2) else "0.0"
            }
        }
    }

    private fun yourTotalShareByMonthAndYear() {
        lifecycleScope.launch {
            viewModel.yourTotalShareByMonthAndYear.collect {
                tvYourTotalShare.text = if (it != -1.0) it.formatNumber(2) else "0.0"
            }
        }
    }


    fun getCurrentMonth(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("MM")
        return currentDate.format(formatter)
    }

    fun getCurrentYear(): String {
        val currentDate = LocalDate.now()
        return currentDate.year.toString()
    }

    fun getLastMonth(): String {
        val currentDate = LocalDate.now()
        val lastMonthDate = currentDate.minusMonths(1)
        val formatter = DateTimeFormatter.ofPattern("MM")
        return lastMonthDate.format(formatter)
    }

    private fun allTime(groupId: Long, personId: Long) {
        viewModel.getTotalGroupSpending(groupId)
        viewModel.getTotalYouPaidFor(groupId, personId)
        viewModel.getYourTotalShare(groupId)
        totalGroupSpending()
        totalYouPaidFor()
        yourTotalShare()
    }

    private fun thisMonth(groupId: Long, personId: Long) {
        viewModel.getTotalGroupSpendingByMonthAndYear(groupId, getCurrentMonth(), getCurrentYear())
        viewModel.getTotalYouPaidForByMonthAndYear(
            groupId,
            personId,
            getCurrentMonth(),
            getCurrentYear()
        )
        viewModel.getYourTotalShareByMonthAndYear(groupId, getCurrentMonth(), getCurrentYear())
        totalGroupSpendingForThisMonth()
        totalYouPaidForForThisMonth()
        yourTotalShareByMonthAndYear()
    }

    private fun lastMonth(groupId: Long, personId: Long) {
        viewModel.getTotalGroupSpendingByMonthAndYear(groupId, getLastMonth(), getCurrentYear())
        viewModel.getTotalYouPaidForByMonthAndYear(
            groupId,
            personId,
            getLastMonth(),
            getCurrentYear()
        )
        viewModel.getYourTotalShareByMonthAndYear(groupId, getLastMonth(), getCurrentYear())
        totalGroupSpendingForThisMonth()
        totalYouPaidForForThisMonth()
        yourTotalShareByMonthAndYear()
    }
}