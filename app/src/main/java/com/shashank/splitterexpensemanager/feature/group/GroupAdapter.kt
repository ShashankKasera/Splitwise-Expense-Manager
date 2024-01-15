package com.shashank.splitterexpensemanager.feature.group

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R

class GroupAdapter : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.group_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("njfnk", "onBindViewHolder: ")
    }

    override fun getItemCount(): Int {
        return 5
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivGroup: ImageView = itemView.findViewById(R.id.iv_group_icon)
        val tvNoExpenses: TextView = itemView.findViewById(R.id.tv_no_expenses)
        val tvOveOrOved: TextView = itemView.findViewById(R.id.tv_ove_or_oved)
        val tvYouOweFist: TextView = itemView.findViewById(R.id.tv_you_owe_fist)
        val tvOweAmountFist: TextView = itemView.findViewById(R.id.tv_owe_amount_fist)
        val tvYouOweSecond: TextView = itemView.findViewById(R.id.tv_you_owe_second)
        val tvOweAmountSecond: TextView = itemView.findViewById(R.id.tv_owe_amount_second)
        val tvPlus: TextView = itemView.findViewById(R.id.tv_plus)
    }
}