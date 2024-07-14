package com.shashank.splitterexpensemanager.feature.addfriends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.model.GroupMember
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.CommonImages

class AddFriendsAdapter(
    private val groupId: Long,
    private val person: List<Person>,
    private val groupMember: List<GroupMember>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<AddFriendsAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(position: Int, data: Person, isChecked: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        holder.tvName.text = person[position].name
        holder.tvNumber.text = person[position].number.toString()
        Glide.with(context).load(CommonImages.USER_ICON).into(holder.civImage)

        holder.checkBox.isChecked = false
        for (i in 0 until groupMember.size) {
            if (person[position].id == groupMember[i].personId && groupMember[i].groupId == groupId) {
                holder.checkBox.isChecked = true
                break
            }
        }
        holder.checkBox.setOnClickListener {
            onItemClickListener.onItemClick(position, person[position], holder.checkBox.isChecked)
        }
    }

    override fun getItemCount(): Int {
        return person.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val civImage: ImageView = itemView.findViewById(R.id.civ_user)
        val tvName: TextView = itemView.findViewById(R.id.tv_user_name)
        val tvNumber: TextView = itemView.findViewById(R.id.tv_user_number)
        var checkBox: CheckBox = itemView.findViewById(R.id.cb_add_friends)
    }
}