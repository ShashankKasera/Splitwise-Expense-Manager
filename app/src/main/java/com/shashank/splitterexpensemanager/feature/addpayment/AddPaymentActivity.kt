package com.shashank.splitterexpensemanager.feature.addpayment

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
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
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

        val groupId: Long = intent.extras?.getLong(GROUP_ID) ?: 0
        val payerId: Long = intent.extras?.getLong(PAYER_ID) ?: 0
        val amount: Double = intent.extras?.getDouble(AMOUNT) ?: 0.0
        val receiverId: Long = intent.extras?.getLong(RECEIVER_ID) ?: 0
        init()

        etAmount.setText(amount.formatNumber(2))
        tvDate.text = getCurrentDate()
        tvTime.text = getCurrentTime()

        clDate.setOnClickListener {
            getDatePicker()
        }

        clTime.setOnClickListener {
            getTimePicker()
        }
        cvSave.setOnClickListener {
            getInitialData(payerId, receiverId, groupId)
        }
        getPayer(payerId)
        getReceiver(receiverId)
    }

    private fun init() {
        tvPayerName = findViewById(R.id.tv_payer_name_repay)
        tvReceiverName = findViewById(R.id.tv_receiver_name_repay)
        clDate = findViewById(R.id.cl_date)
        tvDate = findViewById(R.id.tv_date_repay)
        llDatePicker = findViewById(R.id.ll_dp_repay)
        datePicker = findViewById(R.id.dp_repay)
        clTime = findViewById(R.id.cl_time)
        tvTime = findViewById(R.id.tv_time_repay)
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
            val month = month + 1
            val msg = "$day/$month/$year"
            tvDate.text = msg
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

    private fun getInitialData(payerId: Long, receiverId: Long, groupId: Long) {
        val amount = etAmount.text.toString().trim().toDouble()
        val date = tvDate.text.toString().trim()
        val time = tvTime.text.toString().trim()
        val description = tvDescription.text.toString().trim()

        lifecycleScope.launch {
            viewModel.insertRepay(payerId, receiverId, groupId, amount, date, time, description)
        }

        lifecycleScope.launch {
            viewModel.repayInsertSuccessfully.collect {
                if (it) finish()
            }
        }
    }
}