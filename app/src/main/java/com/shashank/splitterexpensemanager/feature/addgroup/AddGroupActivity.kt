package com.shashank.splitterexpensemanager.feature.addgroup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.localdb.model.Group
import com.shashank.splitterexpensemanager.localdb.model.GroupType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        recyclerViewSetup()
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

        lifecycleScope.launch {
            viewModel.insertGroup(
                Group(null, sGroupName, sGroupType, ""),
            )
        }
    }

    private fun recyclerViewSetup() {
        groupTypeList.add(GroupType("Other", R.drawable.other_png))
        groupTypeList.add(GroupType("Couple", R.drawable.couple_png))
        groupTypeList.add(GroupType("Home", R.drawable.home_rent_icon_png))
        groupTypeList.add(GroupType("Trip", R.drawable.trip_png))


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