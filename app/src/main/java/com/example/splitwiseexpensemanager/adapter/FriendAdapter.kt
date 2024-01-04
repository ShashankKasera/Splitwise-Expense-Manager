package com.example.splitwiseexpensemanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.splitwiseexpensemanager.R

class FriendAdapter () : RecyclerView.Adapter<FriendAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.friend_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        val ItemsViewModel = mList[position]
//        holder.ivGroup.setImageResource(ItemsViewModel.image)
//        holder.tvNoExpenses.text = ItemsViewModel.text

    }

    override fun getItemCount(): Int {
//        return mList.size
        return 10
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val civFriendImage: ImageView = itemView.findViewById(R.id.civ_friend)
        val tvFriendName: TextView = itemView.findViewById(R.id.tv_friend_name)
        val tvFriendOweOrOwed: TextView = itemView.findViewById(R.id.tv_friend_owe_or_owed)
        val tvFriendOweOrOwedAmount: TextView = itemView.findViewById(R.id.tv_friend_owe_or_owed_amount)

    }
}