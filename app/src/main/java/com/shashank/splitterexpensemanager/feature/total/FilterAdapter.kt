package com.shashank.splitterexpensemanager.feature.total

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R

class FilterAdapter(
    private val filterList: List<String>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(filter: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.filter_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filter = filterList[position]

        holder.tvName.text = filter

        holder.clFilter.setOnClickListener {
            onItemClickListener.onItemClick(filter)
        }
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_filter)
        val clFilter: ConstraintLayout = itemView.findViewById(R.id.cl_filter)
    }
}