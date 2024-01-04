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


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GroupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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