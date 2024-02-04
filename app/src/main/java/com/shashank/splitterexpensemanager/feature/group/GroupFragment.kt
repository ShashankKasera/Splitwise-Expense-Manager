package com.shashank.splitterexpensemanager.feature.group

import com.shashank.splitterexpensemanager.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.core.ID
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GroupFragment : Fragment() {

    @Inject
    lateinit var actionProcessor: ActionProcessor
    lateinit var recyclerView: RecyclerView
    lateinit var addGroup: TextView

    private val viewModel: GroupViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_group, container, false)


        recyclerView = v.findViewById(R.id.rv_group)
        addGroup = v.findViewById(R.id.tv_add_group)

        addGroup.setOnClickListener {
            actionProcessor.process(ActionRequestSchema(ActionType.ADD_GROUP.name))
        }
        lifecycleScope.launch {

            viewModel.allGroupLiveData.observe(viewLifecycleOwner) {
                val groupAdapter = GroupAdapter(it,
                    object : GroupAdapter.OnItemClickListener {
                        override fun onItemClick(id: Long) {
                            actionProcessor.process(ActionRequestSchema(ActionType.GROUP_DETAILS.name,
                                hashMapOf(
                                    ID to id
                                )
                            ))
                        }


                    }
                )
                Log.i("jkgtn", "onCreateView: $it")
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = groupAdapter
            }
        }




        return v
    }
}