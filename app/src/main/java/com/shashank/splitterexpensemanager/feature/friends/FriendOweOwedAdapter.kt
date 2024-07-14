package com.shashank.splitterexpensemanager.feature.friends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.shortenName
import com.shashank.splitterexpensemanager.model.FriendOweOrOwed

class FriendOweOwedAdapter(
    private val friendOweOwedList: MutableList<FriendOweOrOwed>
) : RecyclerView.Adapter<FriendOweOwedAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.owe_owed_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friendOweOrOwed = friendOweOwedList[position]
        val context = holder.itemView.context

        val amount = friendOweOrOwed.groupOweOwed
        with(holder) {
            when {
                amount > 0 -> {
                    tvName.text = context.getString(
                        R.string.owes_you_in_group,
                        friendOweOrOwed.friend.name?.shortenName(friendOweOrOwed.friend.name.toString()),
                        friendOweOrOwed.group.groupName
                    )
                    tvAmount.text = context.getString(R.string.rs, amount.formatNumber(2))
                    tvAmount.setTextColor(context.getColor(R.color.green))
                }

                amount < 0 -> {
                    tvName.text = context.getString(
                        R.string.you_owes_in_group,
                        friendOweOrOwed.friend.name?.shortenName(friendOweOrOwed.friend.name.toString()),
                        friendOweOrOwed.group.groupName
                    )
                    tvAmount.text = context.getString(R.string.rs, (-amount).formatNumber(2))
                    tvAmount.setTextColor(context.getColor(R.color.primary_dark))
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return if (friendOweOwedList.size <= 3) friendOweOwedList.size else 2
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_owe_owed_name)
        val tvAmount: TextView = itemView.findViewById(R.id.tv_owe_owed_amount)
    }
}