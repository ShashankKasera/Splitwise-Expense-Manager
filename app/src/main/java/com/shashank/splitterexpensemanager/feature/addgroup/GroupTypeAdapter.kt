package com.shashank.splitterexpensemanager.feature.addgroup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import de.hdodenhof.circleimageview.CircleImageView

class GroupTypeAdapter(
    var groupTypeList: List<GroupType>
) : RecyclerView.Adapter<GroupTypeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.group_type_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = groupTypeList[position].name
        holder.civImage.setImageResource(groupTypeList[position].image)
    }

    override fun getItemCount(): Int {
        return groupTypeList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_group_type_name)
        val civImage: CircleImageView = itemView.findViewById(R.id.civ_group_type_image)
        val llGroupType: LinearLayout = itemView.findViewById(R.id.ll_group_type)
    }
}