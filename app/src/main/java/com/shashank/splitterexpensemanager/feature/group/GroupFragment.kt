package com.shashank.splitterexpensemanager.feature.group

import com.shashank.splitterexpensemanager.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.model.Group
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GroupFragment : Fragment() {

    @Inject
    lateinit var actionProcessor: ActionProcessor
    lateinit var recyclerView: RecyclerView
    lateinit var addGroup: TextView
    lateinit var groupAdapter: GroupAdapter
    private var groupList = mutableListOf<Group>()
    private val viewModel: GroupViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_group, container, false)
        init(v)
        setUpRecyclerView()
        getAllGroups()
        addGroup.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.ADD_GROUP.name,
                )
            )
        }

        return v
    }

    private fun init(v: View) {
        recyclerView = v.findViewById(R.id.rv_group)
        addGroup = v.findViewById(R.id.tv_add_group)
    }

    private fun setUpRecyclerView() {
        groupAdapter = GroupAdapter(
            groupList,
            object : GroupAdapter.OnItemClickListener {
                override fun onItemClick(groupId: Long) {
                    actionProcessor.process(
                        ActionRequestSchema(
                            ActionType.GROUP_DETAILS.name,
                            hashMapOf(
                                GROUP_ID to groupId,
                            )
                        )
                    )
                }
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = groupAdapter
    }

    private fun getAllGroups() {
        lifecycleScope.launch {
            viewModel.getAllGroup()
            viewModel.allGroup.collect {
                if (it.isNotEmpty()) {
                    groupList.clear()
                    groupList.addAll(it)
                    groupAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}