package com.shashank.splitterexpensemanager.feature.activity

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
class ActivityAdapter : RecyclerView.Adapter<ActivityAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("onBindViewHolder", "onBindViewHolder: ")
    }
    override fun getItemCount(): Int {
        return 10
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val civCategory: ImageView = itemView.findViewById(R.id.civ_category)
        val tvName: TextView = itemView.findViewById(R.id.tv_persian_name)
        val tvDate: TextView = itemView.findViewById(R.id.tv_date_activity)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time_activity)
        val tvLentOrBorrowed: TextView = itemView.findViewById(R.id.tv_you_borrowed)
        val tvLentOrBorrowedAmount: TextView = itemView.findViewById(R.id.tv_you_borrowed_amount)
    }
}