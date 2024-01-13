package com.example.splitwiseexpensemanager.fragments

import com.example.splitwiseexpensemanager.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splitwiseexpensemanager.adapter.GroupAdapter


class GroupFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_group, container, false)

        val recyclerView: RecyclerView = v.findViewById(R.id.rv_group)
        val groupAdapter = GroupAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = groupAdapter

        return v
    }
}