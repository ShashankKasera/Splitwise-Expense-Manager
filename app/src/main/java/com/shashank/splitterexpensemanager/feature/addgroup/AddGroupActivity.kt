package com.shashank.splitterexpensemanager.feature.addgroup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R

class AddGroupActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    private var groupTypeList = ArrayList<GroupType>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)

        recyclerViewSetup()
    }

    private fun recyclerViewSetup() {
        groupTypeList.add(GroupType("Other", R.drawable.other_png))
        groupTypeList.add(GroupType("Couple", R.drawable.couple_png))
        groupTypeList.add(GroupType("Home", R.drawable.home_rent_icon_png))
        groupTypeList.add(GroupType("Trip", R.drawable.trip_png))

        recyclerView = findViewById(R.id.rv_group_type)
        val groupTypeAdapter = GroupTypeAdapter(groupTypeList)
        recyclerView.adapter = groupTypeAdapter
        val layoutManager =
            LinearLayoutManager(this@AddGroupActivity, LinearLayoutManager.HORIZONTAL, true)
        recyclerView.layoutManager = layoutManager
    }
}