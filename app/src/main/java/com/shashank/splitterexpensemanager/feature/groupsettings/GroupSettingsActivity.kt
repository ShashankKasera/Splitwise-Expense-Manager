package com.shashank.splitterexpensemanager.feature.groupsettings

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.SharedPref
import com.shashank.splitterexpensemanager.core.UPDATE_GROUP
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.feature.DashboardActivity
import com.shashank.splitterexpensemanager.localdb.model.Group
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GroupSettingsActivity : AppCompatActivity() {

    @Inject
    lateinit var actionProcessor: ActionProcessor

    private val viewModel: GroupSettingsViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    lateinit var tvGroupName: TextView
    lateinit var ivEditGroup: ImageView
    lateinit var cvDeleteGroup: CardView
    lateinit var cvAddFriends: CardView
    lateinit var toolbar: TextView
    lateinit var ivBack: ImageView
    lateinit var groupMemberAdapter: GroupMemberAdapter
    private var groupMemberList = mutableListOf<Pair<Person, Double>>()

    @Inject
    lateinit var sharedPref: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_settings)
        val groupId: Long = intent.extras?.getLong(GROUP_ID) ?: 0
        init(groupId)
        recyclerViewSetup()
        getData()

        ivEditGroup.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.ADD_GROUP.name,
                    hashMapOf(
                        UPDATE_GROUP to true,
                        GROUP_ID to groupId,
                    )
                )
            )
        }
        cvAddFriends.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.ADD_FRIENDS.name,
                    hashMapOf(
                        GROUP_ID to groupId,
                    )
                )
            )
        }
    }

    private fun init(groupId: Long) {
        recyclerView = findViewById(R.id.rv_group_member_group_settings)
        tvGroupName = findViewById(R.id.tv_group_name_group_setting)
        ivEditGroup = findViewById(R.id.iv_edit_group_setting)
        cvDeleteGroup = findViewById(R.id.cv_delete_group_group_settings)
        cvAddFriends = findViewById(R.id.cv_add_group_Member_group_settings)
        toolbar = findViewById(R.id.tv_tb_group_settings)
        ivBack = findViewById(R.id.iv_tb_group_settings)

        toolbar.text = getString(R.string.group_setting)
        ivBack.setOnClickListener {
            finish()
        }
        viewModel.groupSetting(groupId)
    }

    private fun recyclerViewSetup() {
        groupMemberAdapter = GroupMemberAdapter(groupMemberList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = groupMemberAdapter
    }

    private fun getData() {
        lifecycleScope.launch {
            viewModel.groupSetting.collect {
                val group = it.group
                tvGroupName.text = it.group.groupName

                cvDeleteGroup.setOnClickListener {
                    viewModel.deleteGroup(
                        Group(
                            group.id,
                            group.groupName,
                            group.groupType,
                            group.groupName
                        )
                    )
                    val intent = Intent(this@GroupSettingsActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                if (it.groupMembersHashMap.isNotEmpty()) {
                    groupMemberList.clear()
                    groupMemberList.addAll(it.groupMembersHashMap.toList())
                    groupMemberAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}