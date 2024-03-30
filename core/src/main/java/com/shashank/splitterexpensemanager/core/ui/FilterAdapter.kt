package com.shashank.splitterexpensemanager.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.core.R
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import de.hdodenhof.circleimageview.CircleImageView

class FilterAdapter(
    private var selectPosition: Int,
    private val filterList: List<String>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(position: Int, filter: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.filter_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filter = filterList[position]

        holder.tvName.text = filter

        if (position == selectPosition) {
            holder.civFill.visible()
            holder.civUnFill.gone()
        } else {
            holder.civFill.gone()
            holder.civUnFill.visible()
        }
        holder.clFilter.setOnClickListener {
            onItemClickListener.onItemClick(position, filter)
        }
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_filter)
        val clFilter: ConstraintLayout = itemView.findViewById(R.id.cl_filter)
        val civFill: CircleImageView = itemView.findViewById(R.id.civ_fill_filter)
        val civUnFill: CircleImageView = itemView.findViewById(R.id.civ_un_fill_filter)
    }
}