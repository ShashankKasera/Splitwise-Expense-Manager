package com.shashank.splitterexpensemanager.feature.group

import com.shashank.splitterexpensemanager.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.localdb.model.Group
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class GroupFragment : Fragment() {


    lateinit var recyclerView: RecyclerView
    private val viewModel: GroupViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_group, container, false)

        lifecycleScope.launch {
            viewModel.insertAllGroup(
                Group(null, "rameshvaram", "imahe"),
                Group(null, "maharastr", "imahe"),
                Group(null, "gujrat", "imahe"),
                Group(null, "nepal", "imahe"),
                Group(null, "himachal", "imahe"),
                Group(null, "goa", "imahe")
            )

            viewModel.groupLiveData.observe(viewLifecycleOwner) {
                Log.i("gyug", "onCreate: $it")
            }
        }

        recyclerView = v.findViewById(R.id.rv_group)
        val groupAdapter = GroupAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = groupAdapter

        return v
    }
}