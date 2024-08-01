package com.shashank.splitterexpensemanager.feature.addgroup

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.CreateGroupImages
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.GroupTypeImages
import com.shashank.splitterexpensemanager.core.UPDATE_GROUP
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.extension.EMPTY
import com.shashank.splitterexpensemanager.core.extension.capitalizeFirstLetter
import com.shashank.splitterexpensemanager.core.extension.showToast
import com.shashank.splitterexpensemanager.feature.addgroup.model.GroupType
import com.shashank.splitterexpensemanager.feature.groupdetails.GroupDetailsActivity
import com.shashank.splitterexpensemanager.localdb.model.Group
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddGroupActivity : AppCompatActivity() {

    @Inject
    lateinit var actionProcessor: ActionProcessor

    lateinit var recyclerView: RecyclerView
    lateinit var civGroupImage: CircleImageView
    private var groupTypeList = ArrayList<GroupType>()
    private var selectPosition: Int = -1
    private val viewModel: AddGroupViewModel by viewModels()
    private lateinit var groupName: EditText
    private lateinit var tvDone: TextView
    private var sGroupName = String.EMPTY
    private var sGroupType = String.EMPTY
    private var sGroupImage = String.EMPTY
    private lateinit var groupTypeAdapter: GroupTypeAdapter
    private lateinit var ivBack: ImageView

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
                if (validation()) updateGroup(groupId)
                lifecycleScope.launch {
                    viewModel.updateGroup.collect {
                        if (it) {
                            navigateGroupDetails()
                        }
                    }
                }
            } else {
                if (validation()) createGroup()
                lifecycleScope.launch {
                    viewModel.groupAdded.collect {
                        if (it) {
                            navigateGroupDetails()
                        }
                    }
                }
            }
        }
    }

    private fun init() {
        groupName = findViewById(R.id.et_group_name)
        tvDone = findViewById(R.id.tv_done_group)
        ivBack = findViewById(R.id.iv_back_group)
        recyclerView = findViewById(R.id.rv_group_type)
        civGroupImage = findViewById(R.id.civ_group_image)
        ivBack.setOnClickListener {
            finish()
        }
        Glide.with(this@AddGroupActivity).load(CreateGroupImages.GROUP_IMAGE_CREATE_GROUP)
            .into(civGroupImage)
    }

    private fun getDataForUpdateGroup(groupId: Long) {
        lifecycleScope.launch {
            viewModel.loadGroup(groupId)
            viewModel.group.collect {
                groupName.setText(it.groupName)
                sGroupImage = it.groupImage
                Glide.with(this@AddGroupActivity).load(it.groupImage).into(civGroupImage)

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
        viewModel.updateGroup(
            Group(
                groupId,
                sGroupName.capitalizeFirstLetter(sGroupName),
                sGroupType,
                sGroupImage
            )
        )
    }

    private fun createGroup() {
        viewModel.insertGroup(
            this.sGroupName.capitalizeFirstLetter(sGroupName),
            sGroupType,
            sGroupImage
        )
    }

    private fun setUpRecyclerView() {
        groupTypeList.add(
            GroupType(
                getString(R.string.trip),
                GroupTypeImages.TRIP_CREATE_GROUP
            )
        )
        groupTypeList.add(
            GroupType(
                getString(R.string.home),
                GroupTypeImages.HOME_CREATE_GROUP
            )
        )
        groupTypeList.add(
            GroupType(
                getString(R.string.couple),
                GroupTypeImages.COUPLE_CREATE_GROUP
            )
        )
        groupTypeList.add(GroupType(getString(R.string.other), GroupTypeImages.OTHER_CREATE_GROUP))

        groupTypeAdapter = GroupTypeAdapter(
            this,
            selectPosition,
            groupTypeList,
            object : GroupTypeAdapter.OnItemClickListener {
                override fun onItemClick(position: Int, data: GroupType) {
                    sGroupType = data.name
                    if (!sGroupType.equals(String.EMPTY)) {
                        when (sGroupType) {
                            getString(R.string.trip) -> {
                                Glide.with(this@AddGroupActivity)
                                    .load(GroupTypeImages.TRIP_CREATE_GROUP).into(civGroupImage)
                                sGroupImage = GroupTypeImages.TRIP_CREATE_GROUP
                            }

                            getString(R.string.couple) -> {
                                Glide.with(this@AddGroupActivity)
                                    .load(GroupTypeImages.COUPLE_CREATE_GROUP).into(civGroupImage)
                                sGroupImage = GroupTypeImages.COUPLE_CREATE_GROUP
                            }

                            getString(R.string.home) -> {
                                Glide.with(this@AddGroupActivity)
                                    .load(GroupTypeImages.HOME_CREATE_GROUP).into(civGroupImage)
                                sGroupImage = GroupTypeImages.HOME_CREATE_GROUP
                            }

                            getString(R.string.other) -> {
                                Glide.with(this@AddGroupActivity)
                                    .load(GroupTypeImages.OTHER_CREATE_GROUP).into(civGroupImage)
                                sGroupImage = GroupTypeImages.OTHER_CREATE_GROUP
                            }
                        }
                    }
                }
            }
        )
        recyclerView.adapter = groupTypeAdapter
        val layoutManager =
            LinearLayoutManager(this@AddGroupActivity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
    }

    private fun validation() = when {
        sGroupName.isEmpty() -> {
            showToast(getString(R.string.please_enter_group_name))
            false
        }

        sGroupType.isEmpty() -> {
            showToast(getString(R.string.please_select_group_type))
            false
        }

        else -> true
    }

    private fun navigateGroupDetails() {
        val intent = Intent(this@AddGroupActivity, GroupDetailsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra(GROUP_ID, viewModel.groupId)
        startActivity(intent)
        finish()
    }
}