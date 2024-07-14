package com.shashank.splitterexpensemanager.feature.groupmember

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.CommonImages
import de.hdodenhof.circleimageview.CircleImageView

class GroupMemberAdapter(
    private val person: List<Person>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<GroupMemberAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(data: Person)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.group_member_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        holder.tvName.text = person[position].name
        holder.tvNumber.text = person[position].number
        holder.cvGroupMember.setOnClickListener {
            onItemClickListener.onItemClick(person[position])
        }
        Glide.with(context).load(CommonImages.USER_ICON).into(holder.civGroupMember)
    }

    override fun getItemCount(): Int {
        return person.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_group_member_name)
        val tvNumber: TextView = itemView.findViewById(R.id.tv_group_member_number)
        val cvGroupMember: CardView = itemView.findViewById(R.id.cv_add_group_member)
        val civGroupMember: CircleImageView = itemView.findViewById(R.id.civ_group_member)
    }
}