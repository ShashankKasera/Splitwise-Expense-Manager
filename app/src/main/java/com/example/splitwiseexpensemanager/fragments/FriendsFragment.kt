package com.example.splitwiseexpensemanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splitwiseexpensemanager.R
import com.example.splitwiseexpensemanager.adapter.FriendAdapter
import com.example.splitwiseexpensemanager.adapter.GroupAdapter


class FriendsFragment : Fragment() {
    lateinit var recyclerView : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_friends, container, false)

        recyclerView = v.findViewById<View>(R.id.rv_friend) as RecyclerView
        val friendAdapter = FriendAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context,)
        recyclerView.adapter = friendAdapter
        return v
    }
}