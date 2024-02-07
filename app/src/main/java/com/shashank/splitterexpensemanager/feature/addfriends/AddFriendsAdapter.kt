package com.shashank.splitterexpensemanager.feature.addfriends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.localdb.model.GroupMember
import com.shashank.splitterexpensemanager.localdb.model.Person

class AddFriendsAdapter(
    var people: List<Person>,
    var groupMember: List<GroupMember>,
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
        holder.tvName.text = people[position].name
        holder.tvNumber.text = people[position].number.toString()
        for (i in 0..groupMember.size - 1) {
            if (people[position].id == groupMember[i].personId) {
                holder.checkBox.isChecked = true
            }
        }

        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            onItemClickListener.onItemClick(position, people[position], isChecked)
        }
    }

    override fun getItemCount(): Int {
        return people.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val civImage: ImageView = itemView.findViewById(R.id.civ_user)
        val tvName: TextView = itemView.findViewById(R.id.tv_user_name)
        val tvNumber: TextView = itemView.findViewById(R.id.tv_user_number)
        var checkBox: CheckBox = itemView.findViewById(R.id.cb_add_friends)
    }
}