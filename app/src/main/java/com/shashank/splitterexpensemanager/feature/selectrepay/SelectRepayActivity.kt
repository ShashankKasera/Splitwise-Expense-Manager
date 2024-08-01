package com.shashank.splitterexpensemanager.feature.selectrepay

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.PAYER_ID
import com.shashank.splitterexpensemanager.core.RECEIVER_ID
import com.shashank.splitterexpensemanager.core.SELECT_REPAY
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SelectRepayActivity : AppCompatActivity() {

    @Inject
    lateinit var actionProcessor: ActionProcessor
    private val viewModel: SelectRepayViewModel by viewModels()
    private var groupMemberList = mutableListOf<Person>()
    lateinit var groupMemberAdapter: GroupMemberAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var tvSelectPayerReceiver: TextView
    lateinit var ivBack: ImageView
    private var selectPayer: Int = -1
    private var selectRecipient: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_repay)
        val groupId = intent.getLongExtra(GROUP_ID, 0)
        init()
        recyclerViewSetup(groupId)
        getGroupMember(groupId)
    }

    override fun onRestart() {
        super.onRestart()
        selectRecipient = -1
        groupMemberAdapter.selectRecipientPosition(-1)
    }

    private fun init() {
        recyclerView = findViewById(R.id.rv_group_member_select_repay)
        tvSelectPayerReceiver = findViewById(R.id.tv_select_payer_or_receiver)
        ivBack = findViewById(R.id.iv_tb_back_select_payer_or_receiver)
        ivBack.setOnClickListener {
            finish()
        }
    }

    private fun recyclerViewSetup(groupId: Long) {
        var payer: Person = Person()
        var recipient: Person = Person()
        groupMemberAdapter = GroupMemberAdapter(
            selectPayer,
            selectRecipient,
            groupMemberList,
            object : GroupMemberAdapter.OnItemClickListener {
                override fun onItemClick(position: Int, data: Person, checked: Boolean) {
                    if (checked) {
                        if (selectPayer == -1) {
                            selectPayer = position
                            payer = data
                            groupMemberAdapter.selectPayerPosition(position)
                            tvSelectPayerReceiver.text = getString(R.string.select_recipient)
                        } else {
                            selectRecipient = position
                            recipient = data
                            groupMemberAdapter.selectRecipientPosition(position)
                            navigationForAddPayment(payer, recipient, groupId)
                        }
                    } else {
                        if (selectPayer != -1) {
                            selectPayer = -1
                            groupMemberAdapter.selectPayerPosition(-1)
                            tvSelectPayerReceiver.text = getString(R.string.select_payer)
                        }
                        if (selectRecipient != -1) {
                            selectRecipient = -1
                            groupMemberAdapter.selectRecipientPosition(-1)
                        }
                    }
                }
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = groupMemberAdapter
    }


    private fun getGroupMember(groupId: Long) {
        viewModel.allGroupMember(groupId)
        lifecycleScope.launch {
            viewModel.allPerson.collect {
                if (it.isNotEmpty()) {
                    groupMemberList.clear()
                    groupMemberList.addAll(it)
                    groupMemberAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun navigationForAddPayment(payer: Person, recipient: Person, groupId: Long) {
        actionProcessor.process(
            ActionRequestSchema(
                ActionType.ADD_PAYMENT.name,
                hashMapOf(
                    PAYER_ID to (payer.id ?: -1),
                    RECEIVER_ID to (recipient.id ?: -1),
                    GROUP_ID to (groupId),
                    SELECT_REPAY to (true),
                )
            )
        )
    }
}