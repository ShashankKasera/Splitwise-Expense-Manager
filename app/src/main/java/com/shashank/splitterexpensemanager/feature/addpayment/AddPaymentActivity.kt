package com.shashank.splitterexpensemanager.feature.addpayment

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TimePicker
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.AMOUNT
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.PAYER_ID
import com.shashank.splitterexpensemanager.core.RECEIVER_ID
import com.shashank.splitterexpensemanager.core.REPAY_ID
import com.shashank.splitterexpensemanager.core.SELECT_REPAY
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.UPDATE_REPAY
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.feature.groupdetails.GroupDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class AddPaymentActivity : AppCompatActivity() {

    @Inject
    lateinit var actionProcessor: ActionProcessor

    @Inject
    lateinit var sharedPref: SharedPref
    lateinit var llDatePicker: LinearLayout
    lateinit var tvPayerName: TextView
    lateinit var tvReceiverName: TextView
    lateinit var tvWhoPayName: TextView
    lateinit var tvWhoSplitName: TextView
    lateinit var tvDate: TextView
    lateinit var clDate: ConstraintLayout
    lateinit var datePicker: DatePicker
    lateinit var llTimePicker: LinearLayout
    lateinit var tvTime: TextView
    lateinit var clTime: ConstraintLayout
    lateinit var timePicker: TimePicker
    lateinit var etAmount: EditText
    lateinit var tvDescription: EditText
    lateinit var cvSave: CardView
    private val viewModel: AddPaymentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_payment)

        val groupId: Long = intent.extras?.getLong(GROUP_ID) ?: -1
        val payerId: Long = intent.extras?.getLong(PAYER_ID) ?: -1
        val amount: Double = intent.extras?.getDouble(AMOUNT) ?: 0.0
        val repayId: Long = intent.extras?.getLong(REPAY_ID) ?: -1
        val receiverId: Long = intent.extras?.getLong(RECEIVER_ID) ?: -1
        val selectRepay: Boolean = intent.extras?.getBoolean(SELECT_REPAY) ?: false
        val updateFlag: Boolean = intent.extras?.getBoolean(UPDATE_REPAY) ?: false
        init()

        if (updateFlag) {
            viewModel.loadOweOrOwed(repayId)
            getInitialDataRepayForUpdate(repayId)
        } else {
            getInitialDataRepayForInsert(amount, payerId, receiverId)
        }

        clDate.setOnClickListener {
            getDatePicker()
        }

        clTime.setOnClickListener {
            getTimePicker()
        }
        cvSave.setOnClickListener {
            val amount = etAmount.text.toString().trim().toDouble()
            val date = tvDate.text.toString().trim()
            val time = tvTime.text.toString().trim()
            val description = tvDescription.text.toString().trim()

            if (updateFlag) {
                updateRepay(
                    repayId,
                    payerId,
                    receiverId,
                    groupId,
                    amount,
                    date,
                    time,
                    description,
                    selectRepay
                )
            } else {
                getInitialData(
                    payerId,
                    receiverId,
                    groupId,
                    amount,
                    date,
                    time,
                    description,
                    selectRepay
                )
            }
        }
    }

    private fun getInitialDataRepayForInsert(amount: Double, payerId: Long, receiverId: Long) {
        if (amount != 0.0) {
            etAmount.setText(amount.formatNumber(2))
        }
        tvDate.text = getCurrentDate()
        tvTime.text = getCurrentTime()
        getPayer(payerId)
        getReceiver(receiverId)
    }

    private fun init() {
        tvPayerName = findViewById(R.id.tv_payer_name_repay)
        tvReceiverName = findViewById(R.id.tv_receiver_name_repay)
        clDate = findViewById(R.id.cl_date)
        tvDate = findViewById(R.id.tv_date_repay_activity)
        llDatePicker = findViewById(R.id.ll_dp_repay)
        datePicker = findViewById(R.id.dp_repay)
        clTime = findViewById(R.id.cl_time)
        tvTime = findViewById(R.id.tv_time_repay_activity)
        llTimePicker = findViewById(R.id.ll_TimePicker_repay)
        timePicker = findViewById(R.id.timePicker_repay)
        etAmount = findViewById(R.id.et_amount_repay)
        tvDescription = findViewById(R.id.et_description_repay)
        tvWhoPayName = findViewById(R.id.tv_who_pay_repay)
        tvWhoSplitName = findViewById(R.id.tv_who_split)
        cvSave = findViewById(R.id.cv_save_repay)
    }

    private fun getPayer(payerId: Long) {
        viewModel.getPayer(payerId)
        lifecycleScope.launch {
            viewModel.payer.collect {
                if (it?.name?.isNotEmpty() == true) {
                    tvPayerName.text = it.name
                    tvWhoPayName.text = it.name
                }
            }
        }
    }

    private fun getReceiver(receiverId: Long) {
        viewModel.getReceiver(receiverId)
        lifecycleScope.launch {
            viewModel.receiver.collect {
                if (it?.name?.isNotEmpty() == true) {
                    tvReceiverName.text = it.name
                    tvWhoSplitName.text = it.name
                }
            }
        }
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun getDatePicker() {
        llDatePicker.visible()
        val today = Calendar.getInstance()
        datePicker.init(
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val formattedDate = String.format("%02d/%02d/%d", day, month + 1, year)
            tvDate.text = formattedDate
            llDatePicker.gone()
        }
    }

    private fun getCurrentTime(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun getTimePicker() {
        llTimePicker.visible()
        timePicker.setOnTimeChangedListener { _, hour, minute ->
            var hour = hour
            var ampm = ""
            when {
                hour == 0 -> {
                    hour += 12
                    ampm = "AM"
                }

                hour == 12 -> ampm = "PM"
                hour > 12 -> {
                    hour -= 12
                    ampm = "PM"
                }

                else -> ampm = "AM"
            }
            val msg =
                "${if (hour < 10) "0" + hour else hour} : ${if (minute < 10) "0" + minute else minute} $ampm"
            tvTime.text = msg
            llTimePicker.gone()
        }
    }

    private fun getInitialData(
        payerId: Long,
        receiverId: Long,
        groupId: Long,
        amount: Double,
        date: String,
        time: String,
        description: String,
        selectRepay: Boolean
    ) {
        lifecycleScope.launch {
            viewModel.insertRepay(payerId, receiverId, groupId, amount, date, time, description)
        }

        lifecycleScope.launch {
            viewModel.repayInsertSuccessfully.collect {
                if (it) {
                    if (selectRepay) {
                        val intent =
                            Intent(this@AddPaymentActivity, GroupDetailsActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra(GROUP_ID, groupId)
                        startActivity(intent)
                    }
                    finish()
                }
            }
        }
    }

    private fun updateRepay(
        repayId: Long,
        payerId: Long,
        receiverId: Long,
        groupId: Long,
        amount: Double,
        date: String,
        time: String,
        description: String,
        selectRepay: Boolean
    ) {
        lifecycleScope.launch {
            viewModel.oweOrOwedFetchedForUpdate.collect {
                if (it) {
                    viewModel.updateRepay(
                        repayId,
                        payerId,
                        receiverId,
                        groupId,
                        amount,
                        date,
                        time,
                        description
                    )
                }
            }
        }

        lifecycleScope.launch {
            viewModel.repayInsertSuccessfully.collect {
                if (it) {
                    if (selectRepay) {
                        val intent =
                            Intent(this@AddPaymentActivity, GroupDetailsActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra(GROUP_ID, groupId)
                        startActivity(intent)
                    }
                    finish()
                }
            }
        }
    }

    private fun getInitialDataRepayForUpdate(repayId: Long) {
        lifecycleScope.launch {
            viewModel.loadRepayDetails(repayId)
            viewModel.repayDetails.collect {
                etAmount.setText(it?.repay?.amount.toString())
                tvDate.text = it?.repay?.date
                tvTime.text = it?.repay?.time
                tvDescription.setText(it?.repay?.description)
                tvPayerName.text = it?.payer?.name
                tvWhoPayName.text = it?.payer?.name
                tvReceiverName.text = it?.receiver?.name
                tvWhoSplitName.text = it?.receiver?.name
            }
        }
    }
}