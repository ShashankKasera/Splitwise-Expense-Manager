package com.shashank.splitterexpensemanager.feature.repaydetails

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.PAYER_ID
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.RECEIVER_ID
import com.shashank.splitterexpensemanager.core.REPAY_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.TotalImages
import com.shashank.splitterexpensemanager.core.UPDATE_REPAY
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.EMPTY
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RepayDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPref: SharedPref
    lateinit var tvAmount: TextView
    lateinit var civRepay: CircleImageView
    lateinit var tvname: TextView
    lateinit var tvDateTime: TextView
    lateinit var ivDelete: ImageView
    lateinit var ivUpdate: ImageView
    lateinit var tvdescription: TextView
    lateinit var toolbar: TextView
    lateinit var ivBack: ImageView

    @Inject
    lateinit var actionProcessor: ActionProcessor
    private val viewModel: RepayDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repay_details)

        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long
        val repayId: Long = intent.extras?.getLong(REPAY_ID) ?: -1
        val groupId: Long = intent.extras?.getLong(GROUP_ID) ?: -1

        var payerId: Long = -1
        var receiverId: Long = -1
        init()

        viewModel.loadRepayDetails(repayId)
        lifecycleScope.launch {
            viewModel.repay.collect {
                if (it != null) {
                    payerId = it.payer.id ?: -1
                    receiverId = it.receiver.id ?: -1
                    tvAmount.text = it.repay.amount?.formatNumber(2)
                    Glide.with(this@RepayDetailsActivity).load(TotalImages.TOTAL_YOU_PAID_FOR)
                        .into(civRepay)
                    tvname.text = if (it.payer.id == personId) {
                        getString(R.string.you_paid, it.receiver.name)
                    } else {
                        getString(R.string.payer_receiver, it.payer.name, it.receiver.name)
                    }
                    tvDateTime.text = getString(R.string.date_time, it.repay.date, it.repay.time)

                    if (it.repay.description != String.EMPTY) {
                        tvdescription.visible()
                        tvdescription.text =
                            getString(R.string.description_expenses_details, it.repay.description)
                    } else {
                        tvdescription.gone()
                    }
                }
            }
        }
        ivUpdate.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.ADD_PAYMENT.name,
                    hashMapOf(
                        UPDATE_REPAY to true,
                        REPAY_ID to repayId,
                        PAYER_ID to payerId,
                        RECEIVER_ID to receiverId,
                        GROUP_ID to groupId,
                    )
                )
            )
        }
        ivDelete.setOnClickListener {
            viewModel.deleteRepay(repayId)
            finish()
        }
    }

    private fun init() {
        tvAmount = findViewById(R.id.tv_amount_repay_details)
        civRepay = findViewById(R.id.civ_repay)
        tvname = findViewById(R.id.tv_name_repay_details)
        tvDateTime = findViewById(R.id.tv_date_time)

        tvdescription = findViewById(R.id.tv_description)
        ivDelete = findViewById(R.id.iv_delete_repay_details)
        ivUpdate = findViewById(R.id.iv_edit_repay_details)
        toolbar = findViewById(R.id.tv_tb_repay_details)
        ivBack = findViewById(R.id.iv_back_repay_details)

        toolbar.text = getString(R.string.repay_details)
        ivBack.setOnClickListener {
            finish()
        }
    }
}