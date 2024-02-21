package com.shashank.splitterexpensemanager.feature.addgroup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.feature.addgroup.model.GroupType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddGroupActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    private var groupTypeList = ArrayList<GroupType>()
    private val viewModel: AddGroupViewModel by viewModels()
    private lateinit var groupName: EditText
    private lateinit var tvDone: TextView
    private lateinit var sGroupName: String
    private lateinit var sGroupType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)

        init()
        setUpRecyclerView()
        tvDone.setOnClickListener {
            createGroup()
            finish()
        }
    }

    private fun init() {
        groupName = findViewById(R.id.et_group_name)
        tvDone = findViewById(R.id.tv_done)
        recyclerView = findViewById(R.id.rv_group_type)
    }

    private fun createGroup() {
        sGroupName = groupName.text.toString().trim()
        viewModel.insertGroup(sGroupName, sGroupType)
    }

    private fun setUpRecyclerView() {
        groupTypeList.add(GroupType(getString(R.string.other), R.drawable.other_png))
        groupTypeList.add(GroupType(getString(R.string.couple), R.drawable.couple_png))
        groupTypeList.add(GroupType(getString(R.string.home), R.drawable.home_rent_icon_png))
        groupTypeList.add(GroupType(getString(R.string.trip), R.drawable.trip_png))

        val groupTypeAdapter =
            GroupTypeAdapter(
                groupTypeList,
                object : GroupTypeAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int, data: GroupType) {
                        sGroupType = data.name
                    }
                }
            )
        recyclerView.adapter = groupTypeAdapter
        val layoutManager =
            LinearLayoutManager(this@AddGroupActivity, LinearLayoutManager.HORIZONTAL, true)
        recyclerView.layoutManager = layoutManager
    }
}