package com.shashank.splitterexpensemanager.feature.balances

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.model.Balances
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BalancesActivity : AppCompatActivity() {
    lateinit var balancesAdapter: BalancesAdapter
    lateinit var recyclerView: RecyclerView
    private var groupMemberList = mutableListOf<Balances>()
    private val viewModel: BalancesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balances)
        val groupId: Long = intent.extras?.getLong(GROUP_ID) ?: 0
        recyclerView = findViewById(R.id.rv_balances)
        setUpRecyclerView()
        viewModel.getBalances(groupId)
        lifecycleScope.launch {
            viewModel.allBalance.collect {
                if (it.isNotEmpty()) {
                    groupMemberList.addAll(it)
                    balancesAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        balancesAdapter = BalancesAdapter(this, groupMemberList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = balancesAdapter
    }
}