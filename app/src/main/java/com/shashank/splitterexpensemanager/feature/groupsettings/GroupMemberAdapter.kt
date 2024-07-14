package com.shashank.splitterexpensemanager.feature.groupsettings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.extension.formatNumber

class GroupMemberAdapter(
    private val person: MutableList<Pair<Person, Double>>,
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
        val (person, amount) = person[position]
        val context = holder.itemView.context

        holder.tvName.text = person.name
        holder.tvNumber.text = person.number

        if (amount > 0) {
            holder.tvGetBackOrOwe.text = context.getString(R.string.get_back)
            holder.tvGetBackOrOweAmount.text =
                context.getString(R.string.rs, (amount).formatNumber(2))
            holder.tvGetBackOrOwe.setTextColor(context.getColor(R.color.green))
            holder.tvGetBackOrOweAmount.setTextColor(context.getColor(R.color.green))
        } else if (amount < 0) {
            holder.tvGetBackOrOwe.text = context.getString(R.string.owes_group_member)
            holder.tvGetBackOrOweAmount.text =
                context.getString(R.string.rs, (-amount).formatNumber(2))
            holder.tvGetBackOrOwe.setTextColor(context.getColor(R.color.primary_dark))
            holder.tvGetBackOrOweAmount.setTextColor(context.getColor(R.color.primary_dark))
        }
    }

    override fun getItemCount(): Int {
        return person.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_group_member_name)
        val tvNumber: TextView = itemView.findViewById(R.id.tv_group_member_number)
        val cvGroupMember: CardView = itemView.findViewById(R.id.cv_add_group_member)
        val tvGetBackOrOwe: TextView = itemView.findViewById(R.id.tv_get_back)
        val tvGetBackOrOweAmount: TextView = itemView.findViewById(R.id.tv_get_back_amount)
    }
}