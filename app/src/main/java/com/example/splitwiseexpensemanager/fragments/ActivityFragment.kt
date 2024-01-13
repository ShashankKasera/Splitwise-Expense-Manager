package com.example.splitwiseexpensemanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splitwiseexpensemanager.R
import com.example.splitwiseexpensemanager.adapter.ActivityAdapter

class ActivityFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_activity, container, false)

        recyclerView = v.findViewById<View>(R.id.rv_activity) as RecyclerView
        val activityAdapter = ActivityAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = activityAdapter
        return v
    }
}