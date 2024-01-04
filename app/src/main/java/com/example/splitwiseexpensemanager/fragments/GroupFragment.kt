package com.example.splitwiseexpensemanager.fragments

import com.example.splitwiseexpensemanager.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splitwiseexpensemanager.adapter.GroupAdapter



class GroupFragment : Fragment() {


    lateinit var RecyclerView :RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_group, container, false)

        RecyclerView = v.findViewById<View>(R.id.rv_group) as RecyclerView
        val groupAdapter = GroupAdapter()
        RecyclerView.layoutManager = LinearLayoutManager(context,)
        RecyclerView.adapter = groupAdapter
        return v
    }


}