package com.shashank.splitterexpensemanager.feature.addfriends

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.ID
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.localdb.model.GroupMember
import com.shashank.splitterexpensemanager.localdb.model.Person
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddFriendsActivity : AppCompatActivity() {
    private val viewModel: AddFriendsViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    lateinit var tvAddFriends: TextView
    @Inject
    lateinit var actionProcessor: ActionProcessor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friends)
        val id = intent.extras?.getLong(ID)
        recyclerView = findViewById(R.id.rv_add_friends)
        tvAddFriends = findViewById(R.id.tv_add_User)

        tvAddFriends.setOnClickListener {
            actionProcessor.process(ActionRequestSchema(ActionType.CREATE_FRIENDS.name))
        }

        viewModel.allPersonLiveData.observe(this@AddFriendsActivity){person->
            viewModel.allGroupMemberLiveData.observe(this@AddFriendsActivity){groupMember->
                val activityAdapter = AddFriendsAdapter(
                    person,
                    groupMember,
                    object : AddFriendsAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int, data: Person, isChecked: Boolean) {
                            lifecycleScope.launch {
                                if (isChecked) {
                                    viewModel.insertGroupMember(GroupMember(null,data.id?:0,id?:0))
                                }else{
                                    viewModel.deleteGroupMember(data.id?:0)
                                }

                            }
                        }
                    })
                recyclerView.layoutManager = LinearLayoutManager(this@AddFriendsActivity)
                recyclerView.adapter = activityAdapter
            }

        }


    }


}