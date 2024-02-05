package com.shashank.splitterexpensemanager.feature.addgroup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R

@AndroidEntryPoint
class AddGroupActivity : AppCompatActivity() {
    @Inject
    lateinit var actionProcessor: ActionProcessor
    lateinit var recyclerView: RecyclerView
    private var groupTypeList = ArrayList<GroupType>()
    private var groupTypeList = ArrayList<GroupType>()
    private val viewModel: GroupViewModel by viewModels()
    private lateinit var addMember: TextView
    private lateinit var groupName: EditText
    private lateinit var groupImage: CircleImageView
    private lateinit var tvDone: TextView

    private lateinit var sGroupName: String
    private lateinit var sGroupType: String
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
        addMember = findViewById(R.id.tv_add_member)
        groupName = findViewById(R.id.et_group_name)
        groupImage = findViewById(R.id.civ_group_image)
        tvDone = findViewById(R.id.tv_done)

        recyclerViewSetup()
        tvDone.setOnClickListener {
            createGroup()
            finish()
        }
    }

    private fun createGroup() {
        sGroupName = groupName.text.toString().trim()

        lifecycleScope.launch {
            viewModel.insertGroup(
                Group(null, sGroupName, sGroupType, "Group_image"),
            )
        }
    }

    private fun recyclerViewSetup() {
        groupTypeList.add(GroupType("Other", R.drawable.other_png))
        groupTypeList.add(GroupType("Couple", R.drawable.couple_png))
        groupTypeList.add(GroupType("Home", R.drawable.home_rent_icon_png))
        groupTypeList.add(GroupType("Trip", R.drawable.trip_png))

        recyclerView = findViewById(R.id.rv_group_type)
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