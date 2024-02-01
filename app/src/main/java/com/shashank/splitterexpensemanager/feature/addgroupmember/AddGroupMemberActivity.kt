package com.shashank.splitterexpensemanager.feature.addgroupmember

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.localdb.model.GroupMember
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddGroupMemberActivity : AppCompatActivity() {

    private val viewModel: AddGroupMemberViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group_member)

        lifecycleScope.launch {
            viewModel.insertAllGroupMember(
                GroupMember(null, 1, 1),
                GroupMember(null, 1, 1)
            )

            viewModel.groupMemberLiveData.observe(this@AddGroupMemberActivity) {
                Log.i("gyug", "onCreate: $it")
            }
        }
    }
}