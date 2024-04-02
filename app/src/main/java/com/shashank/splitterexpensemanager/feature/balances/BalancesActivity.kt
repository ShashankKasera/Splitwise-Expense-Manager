package com.shashank.splitterexpensemanager.feature.balances

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.model.Balances
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BalancesActivity : AppCompatActivity() {
    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var actionProcessor: ActionProcessor
    lateinit var balancesAdapter: BalancesAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var toolbar: TextView
    lateinit var ivBack: ImageView
    private var groupMemberList = mutableListOf<Balances>()
    private val viewModel: BalancesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balances)
        val groupId: Long = intent.extras?.getLong(GROUP_ID) ?: 0
        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long
        recyclerView = findViewById(R.id.rv_balances)
        toolbar = findViewById(R.id.tv_tb_balances)
        ivBack = findViewById(R.id.iv_tb_balances)

        toolbar.text = getString(R.string.balances)
        ivBack.setOnClickListener {
            finish()
        }
        setUpRecyclerView(groupId, personId)
        viewModel.getBalances(groupId)
        lifecycleScope.launch {
            viewModel.allBalance.collect {
                if (it.isNotEmpty()) {
                    groupMemberList.clear()
                    groupMemberList.addAll(it)
                    balancesAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        val groupId: Long = intent.extras?.getLong(GROUP_ID) ?: 0
        viewModel.getBalances(groupId)
    }

    private fun setUpRecyclerView(groupId: Long, personId: Long) {
        balancesAdapter = BalancesAdapter(actionProcessor, groupId, personId, groupMemberList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = balancesAdapter
    }
}