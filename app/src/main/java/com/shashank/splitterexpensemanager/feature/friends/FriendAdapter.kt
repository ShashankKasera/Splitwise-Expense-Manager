package com.shashank.splitterexpensemanager.feature.friends

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R

class FriendAdapter : RecyclerView.Adapter<FriendAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("onBindViewHolder", "onBindViewHolder: ")
    }

    override fun getItemCount(): Int {
        return 10
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val civFriendImage: ImageView = itemView.findViewById(R.id.civ_friend)
        val tvFriendName: TextView = itemView.findViewById(R.id.tv_friend_name)
        val tvFriendOweOrOwed: TextView = itemView.findViewById(R.id.tv_friend_owe_or_owed)
        val tvFriendOweOrOwedAmount: TextView = itemView.findViewById(R.id.tv_friend_owe_or_owed_amount)
    }
}