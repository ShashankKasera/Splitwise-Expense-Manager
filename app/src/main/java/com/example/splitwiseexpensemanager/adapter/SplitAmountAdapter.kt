package com.example.splitwiseexpensemanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.splitwiseexpensemanager.R

class SplitAmountAdapter () : RecyclerView.Adapter<SplitAmountAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.amount_split_item, parent, false)

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
        val tvName: TextView = itemView.findViewById(R.id.tv_splitter_name)
        val tvAmount: TextView = itemView.findViewById(R.id.tv_splitter_amount)

    }
}