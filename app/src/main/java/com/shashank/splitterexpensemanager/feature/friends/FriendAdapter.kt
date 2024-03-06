package com.shashank.splitterexpensemanager.feature.friends

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible

class FriendAdapter(
    private val allFriendsList: MutableList<Pair<Person, Double>>,
) : RecyclerView.Adapter<FriendAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (person, amount) = allFriendsList[position]
        val context = holder.itemView.context

        holder.tvFriendName.text = person.name

        when {
            amount == 0.0 -> {
                setupSettledUpView(holder, context)
            }

            amount > 0 -> {
                setupOwesView(holder, context, R.string.owes_you_friends, R.color.green, amount)
            }

            amount < 0 -> {
                setupOwesView(holder, context, R.string.you_owes, R.color.primary_dark, -amount)
            }
        }
    }

    private fun setupSettledUpView(holder: ViewHolder, context: Context) {
        holder.tvSettledUp.visible()
        holder.tvFriendOweOrOwed.gone()
        holder.tvFriendOweOrOwedAmount.gone()
        holder.tvSettledUp.text = context.getString(R.string.settled_up)
        holder.tvSettledUp.setTextColor(context.getColor(R.color.dark_grey))
    }

    private fun setupOwesView(
        holder: ViewHolder,
        context: Context,
        textResId: Int,
        textColorResId: Int,
        amount: Double
    ) {
        holder.tvSettledUp.gone()
        holder.tvFriendOweOrOwed.text = context.getString(textResId)
        holder.tvFriendOweOrOwed.setTextColor(context.getColor(textColorResId))
        holder.tvFriendOweOrOwedAmount.text = context.getString(R.string.rs, amount.formatNumber(2))
        holder.tvFriendOweOrOwedAmount.setTextColor(context.getColor(textColorResId))
    }

    override fun getItemCount(): Int {
        return allFriendsList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val civFriendImage: ImageView = itemView.findViewById(R.id.civ_friend)
        val tvFriendName: TextView = itemView.findViewById(R.id.tv_friend_name)
        val tvSettledUp: TextView = itemView.findViewById(R.id.tv_settled_up_friends)
        val tvFriendOweOrOwed: TextView = itemView.findViewById(R.id.tv_friend_owe_or_owed)
        val tvFriendOweOrOwedAmount: TextView =
            itemView.findViewById(R.id.tv_friend_owe_or_owed_amount)
    }
}