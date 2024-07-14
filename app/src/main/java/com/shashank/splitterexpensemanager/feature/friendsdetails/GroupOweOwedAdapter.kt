package com.shashank.splitterexpensemanager.feature.friendsdetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.model.Group
import de.hdodenhof.circleimageview.CircleImageView

class GroupOweOwedAdapter(
    private val actionProcessor: ActionProcessor,
    private val groupOweOwedList: MutableList<Pair<Group, Double>>
) : RecyclerView.Adapter<GroupOweOwedAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.group_owe_owed_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (group, amount) = groupOweOwedList[position]
        val context = holder.itemView.context

        with(holder) {
            tvName.text = group.groupName
            tvGroupType.text = group.groupType
            if (amount == 0.0) {
                tvSettledUp.visible()
                tvAmount.gone()
                tvLentOrBorrowedAmount.gone()
            } else {
                tvSettledUp.gone()
                tvAmount.visible()
                tvLentOrBorrowedAmount.visible()

                val formattedAmount =
                    if (amount > 0) amount.formatNumber(2) else (-amount).formatNumber(2)
                tvAmount.text = formattedAmount

                val isLent = amount > 0
                val lentOrBorrowedString = if (isLent) R.string.you_lent else R.string.you_borrowed

                with(tvLentOrBorrowedAmount) {
                    text = context.getString(lentOrBorrowedString)
                    setTextColor(context.resources.getColor(if (isLent) R.color.green else R.color.primary_dark))
                }
                tvAmount.setTextColor(context.resources.getColor(if (isLent) R.color.green else R.color.primary_dark))
            }
            setImage(context, group, civGroupImage)
            navigationForGroupDetails(cvGroup, group)
        }
    }

    private fun navigationForGroupDetails(cvGroup: CardView, group: Group) {
        cvGroup.setOnClickListener {
            val groupId = group.id ?: -1
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.GROUP_DETAILS.name,
                    hashMapOf(
                        GROUP_ID to groupId,
                    )
                )
            )
        }
    }

    private fun setImage(context: Context, group: Group, civGroupImage: CircleImageView) {
        Glide.with(context).load(group.groupImage)
            .into(civGroupImage)
    }

    override fun getItemCount(): Int {
        return groupOweOwedList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_friend_name)
        val tvGroupType: TextView = itemView.findViewById(R.id.tv_group_type_friend)
        val civGroupImage: CircleImageView = itemView.findViewById(R.id.civ_friends_image)
        val tvLentOrBorrowedAmount: TextView = itemView.findViewById(R.id.tv_you_borrowed_friends)
        val tvAmount: TextView = itemView.findViewById(R.id.tv_you_borrowed_amount_friends)
        val tvSettledUp: TextView = itemView.findViewById(R.id.tv_settled_up_friends)
        val cvGroup: CardView = itemView.findViewById(R.id.cv_friends_details)
    }
}