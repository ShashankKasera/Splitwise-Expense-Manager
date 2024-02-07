package com.shashank.splitterexpensemanager.feature.addgroup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import de.hdodenhof.circleimageview.CircleImageView

class GroupTypeAdapter(
    private var groupTypeList: ArrayList<GroupType>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<GroupTypeAdapter.ViewHolder>() {

    private var selecdPosision = -1

    interface OnItemClickListener {
        fun onItemClick(position: Int, data: GroupType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.group_type_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = groupTypeList[position].name
        holder.civImage.setImageResource(groupTypeList[position].image)

        if (position == selecdPosision) {
            holder.llGroupType.setBackgroundColor(
                holder.llGroupType.context.getResources().getColor(R.color.primary_mid)
            )
            holder.tvName.setTextColor(holder.tvName.context.getResources().getColor(R.color.white))
        } else {
            holder.llGroupType.setBackgroundColor(
                holder.llGroupType.context.getResources().getColor(R.color.white)
            )
            holder.tvName.setTextColor(holder.tvName.context.getResources().getColor(R.color.black))
        }
        holder.tvName.setOnClickListener {
            selecdPosision = position
            notifyDataSetChanged()
            onItemClickListener.onItemClick(position, groupTypeList[position])
        }
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