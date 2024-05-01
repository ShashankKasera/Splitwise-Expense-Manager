package com.shashank.splitterexpensemanager.feature.addgroup

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.UPDATE_GROUP
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.feature.addgroup.model.GroupType
import com.shashank.splitterexpensemanager.localdb.model.Group
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddGroupActivity : AppCompatActivity() {

    @Inject
    lateinit var actionProcessor: ActionProcessor

    lateinit var recyclerView: RecyclerView
    private var groupTypeList = ArrayList<GroupType>()
    private var selectPosition: Int = -1
    private val viewModel: AddGroupViewModel by viewModels()
    private lateinit var groupName: EditText
    private lateinit var tvDone: TextView
    private lateinit var sGroupName: String
    private lateinit var sGroupType: String
    private lateinit var groupTypeAdapter: GroupTypeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)
        val groupId: Long = intent.extras?.getLong(GROUP_ID) ?: 0
        val updateGroupFlag: Boolean = intent.extras?.getBoolean(UPDATE_GROUP) ?: false

        init()
        setUpRecyclerView()

        if (updateGroupFlag) {
            getDataForUpdateGroup(groupId)
        }
        tvDone.setOnClickListener {
            sGroupName = groupName.text.toString().trim()
            if (updateGroupFlag) {
                updateGroup(groupId)
            } else {
                createGroup()
            }
            finish()
        }
    }

    private fun init() {
        groupName = findViewById(R.id.et_group_name)
        tvDone = findViewById(R.id.tv_done)
        recyclerView = findViewById(R.id.rv_group_type)
    }

    private fun getDataForUpdateGroup(groupId: Long) {
        lifecycleScope.launch {
            viewModel.loadGroup(groupId)
            viewModel.group.collect {
                groupName.setText(it.groupName)
                sGroupType = it.groupType
                selectPosition = groupTypeList.indexOfFirst { it.name == sGroupType }
                if (selectPosition != -1) {
                    groupTypeAdapter.selectPosition(selectPosition)
                    groupTypeAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun updateGroup(groupId: Long) {
        viewModel.updateGroup(Group(groupId, sGroupName, sGroupType, ""))
    }

    private fun createGroup() {
        viewModel.insertGroup(this.sGroupName, sGroupType)
    }

    private fun setUpRecyclerView() {
        groupTypeList.add(GroupType(getString(R.string.trip), R.drawable.trip_png))
        groupTypeList.add(GroupType(getString(R.string.home), R.drawable.home_rent_icon_png))
        groupTypeList.add(GroupType(getString(R.string.couple), R.drawable.couple_png))
        groupTypeList.add(GroupType(getString(R.string.other), R.drawable.other_png))

        groupTypeAdapter =
            GroupTypeAdapter(
                selectPosition,
                groupTypeList,
                object : GroupTypeAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int, data: GroupType) {
                        sGroupType = data.name
                    }
                }
            )
        recyclerView.adapter = groupTypeAdapter
        val layoutManager =
            LinearLayoutManager(this@AddGroupActivity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
    }
}