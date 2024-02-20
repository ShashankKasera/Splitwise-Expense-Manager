package com.shashank.splitterexpensemanager.feature.addgroupmember

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shashank.splitterexpensemanager.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddGroupMemberActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group_member)
    }
}