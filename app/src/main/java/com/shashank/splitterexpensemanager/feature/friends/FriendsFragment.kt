package com.shashank.splitterexpensemanager.feature.friends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R

class FriendsFragment : Fragment() {
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_friends, container, false)

        recyclerView = v.findViewById<View>(R.id.rv_friend) as RecyclerView
        recyclerViewSetUp()
        return v
    }

    fun recyclerViewSetUp() {
        val friendAdapter = FriendAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = friendAdapter
    }
}