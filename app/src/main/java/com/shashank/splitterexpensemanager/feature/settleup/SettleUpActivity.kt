package com.shashank.splitterexpensemanager.feature.settleup

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.CommonImages
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.PERSON_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettleUpActivity : AppCompatActivity() {
    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var actionProcessor: ActionProcessor
    lateinit var recyclerView: RecyclerView
    private val viewModel: SettleUpViewModel by viewModels()
    lateinit var settleUpAdapter: SettleUpAdapter
    lateinit var ivCancel: ImageView
    private var oweOwedList = mutableListOf<Pair<Person, Double>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settle_up)

        var groupId: Long = intent.extras?.getLong(GROUP_ID) ?: 0
        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long
        recyclerView = findViewById(R.id.rv_settle_up)
        ivCancel = findViewById(R.id.tv_cancel_settle_up)

        ivCancel.setOnClickListener {
            finish()
        }
        setupRecyclerView(groupId, personId)

        Glide.with(this).load(CommonImages.CANCEL_ICON).into(ivCancel)
        viewModel.settleUp(groupId, personId)
        lifecycleScope.launch {
            viewModel.settleUp.collect {
                oweOwedList.clear()
                oweOwedList.addAll(it)
                oweOwedList.removeIf { it.second == 0.0 }
                settleUpAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        val groupId: Long = intent.extras?.getLong(GROUP_ID) ?: 0
        val personId = sharedPref.getValue(PERSON_ID, 0L) as Long
        viewModel.settleUp(groupId, personId)
    }

    private fun setupRecyclerView(groupId: Long, personId: Long) {
        settleUpAdapter = SettleUpAdapter(groupId, personId, actionProcessor, oweOwedList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = settleUpAdapter
    }
}