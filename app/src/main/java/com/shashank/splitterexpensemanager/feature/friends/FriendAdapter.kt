package com.shashank.splitterexpensemanager.feature.friends

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.model.FriendOweOrOwed
import com.shashank.splitterexpensemanager.model.Friends

class FriendAdapter(
    private val context: FriendsFragment,
    private val allFriendsList: MutableList<Friends>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<FriendAdapter.ViewHolder>() {

    lateinit var friendOweOwedAdapter: FriendOweOwedAdapter
    private var oweOwedList = mutableListOf<FriendOweOrOwed>()

    interface OnItemClickListener {
        fun onItemClick(friend: Person)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friends = allFriendsList[position]
        val context = holder.itemView.context
        setUpRecyclerView(holder)
        holder.tvFriendName.text = friends.friend.name
        oweOwedList.clear()
        oweOwedList.addAll(friends.friendsOweOwedList)
        oweOwedList.removeIf { it.groupOweOwed == 0.0 }
        if (oweOwedList.size > 3) {
            holder.tvPlusOther.visible()
            holder.tvPlusOther.text =
                context.getString(R.string.plus_other_balance, (oweOwedList.size - 2))
        } else {
            holder.tvPlusOther.gone()
        }
        friendOweOwedAdapter.notifyDataSetChanged()

        when {
            friends.overallOweOrOwed == 0.0 -> {
                setupSettledUpView(holder, context)
            }

            friends.overallOweOrOwed > 0 -> {
                setupOwesView(
                    holder,
                    context,
                    R.string.owes_you_friends,
                    R.color.green,
                    friends.overallOweOrOwed
                )
            }

            friends.overallOweOrOwed < 0 -> {
                setupOwesView(
                    holder,
                    context,
                    R.string.you_owes,
                    R.color.primary_dark,
                    -friends.overallOweOrOwed
                )
            }
        }

        holder.cvFriend.setOnClickListener {
            onItemClickListener.onItemClick(allFriendsList[position].friend)
        }
    }

    private fun setupSettledUpView(holder: ViewHolder, context: Context) {
        holder.tvSettledUp.visible()
        holder.tvFriendOweOrOwed.gone()
        holder.tvFriendOweOrOwedAmount.gone()
        holder.tvSettledUp.text = context.getString(R.string.no_expenses_friend)
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
        holder.tvFriendOweOrOwed.visible()
        holder.tvFriendOweOrOwedAmount.visible()
        holder.tvFriendOweOrOwed.text = context.getString(textResId)
        holder.tvFriendOweOrOwed.setTextColor(context.getColor(textColorResId))
        holder.tvFriendOweOrOwedAmount.text = context.getString(R.string.rs, amount.formatNumber(2))
        holder.tvFriendOweOrOwedAmount.setTextColor(context.getColor(textColorResId))
    }

    override fun getItemCount(): Int {
        return allFriendsList.size
    }

    private fun setUpRecyclerView(holder: ViewHolder) {
        friendOweOwedAdapter = FriendOweOwedAdapter(oweOwedList)
        holder.rvFriendOweOwed.layoutManager = LinearLayoutManager(context.context)
        holder.rvFriendOweOwed.adapter = friendOweOwedAdapter
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvFriend: CardView = itemView.findViewById(R.id.cv_friend)
        val rvFriendOweOwed: RecyclerView = itemView.findViewById(R.id.rv_friend_owe_owed_friend)
        val civFriendImage: ImageView = itemView.findViewById(R.id.civ_friend)
        val tvFriendName: TextView = itemView.findViewById(R.id.tv_friend_name)
        val tvSettledUp: TextView = itemView.findViewById(R.id.tv_settled_up_friends)
        val tvFriendOweOrOwed: TextView = itemView.findViewById(R.id.tv_friend_owe_or_owed)
        val tvPlusOther: TextView = itemView.findViewById(R.id.tv_plus_friend)
        val tvFriendOweOrOwedAmount: TextView =
            itemView.findViewById(R.id.tv_friend_owe_or_owed_amount)
    }
}