package com.shashank.splitterexpensemanager.feature.groupsettledup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.AMOUNT
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.PAYER_ID
import com.shashank.splitterexpensemanager.core.RECEIVER_ID
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.model.FriendOweOrOwed
import de.hdodenhof.circleimageview.CircleImageView

class GroupSettleUpAdapter(
    private val personId: Long,
    private val actionProcessor: ActionProcessor,
    private val settledUpList: MutableList<FriendOweOrOwed>
) : RecyclerView.Adapter<GroupSettleUpAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.group_settled_up_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val settledUp = settledUpList[position]
        val context = holder.itemView.context

        holder.tvGroupName.text = settledUp.group.groupName

        with(holder) {
            when {
                settledUp.groupOweOwed > 0 -> {
                    tvGetBackOrOwe.text = context.getString(R.string.you_are_owed)
                    tvGetBackOrOweAmount.text =
                        context.getString(R.string.rs, (settledUp.groupOweOwed).formatNumber(2))
                    tvGetBackOrOwe.setTextColor(context.getColor(R.color.green))
                    tvGetBackOrOweAmount.setTextColor(context.getColor(R.color.green))
                }

                settledUp.groupOweOwed < 0 -> {
                    tvGetBackOrOwe.text = context.getString(R.string.you_owed)
                    tvGetBackOrOweAmount.text =
                        context.getString(R.string.rs, (-settledUp.groupOweOwed).formatNumber(2))
                    tvGetBackOrOwe.setTextColor(context.getColor(R.color.primary_dark))
                    tvGetBackOrOweAmount.setTextColor(context.getColor(R.color.primary_dark))
                }
            }

            Glide.with(context).load(settledUp.group.groupImage)
                .into(civGroupImage)
            cvSettledUp.setOnClickListener {
                when {
                    settledUp.groupOweOwed > 0 -> {
                        actionProcessor.process(
                            ActionRequestSchema(
                                ActionType.ADD_PAYMENT.name,
                                hashMapOf(
                                    PAYER_ID to (settledUp.friend.id ?: -1),
                                    RECEIVER_ID to (personId),
                                    GROUP_ID to (settledUp.group.id ?: -1),
                                    AMOUNT to (settledUp.groupOweOwed),
                                )
                            )
                        )
                    }

                    settledUp.groupOweOwed < 0 -> {
                        actionProcessor.process(
                            ActionRequestSchema(
                                ActionType.ADD_PAYMENT.name,
                                hashMapOf(
                                    PAYER_ID to (personId),
                                    RECEIVER_ID to (settledUp.friend.id ?: -1),
                                    GROUP_ID to (settledUp.group.id ?: -1),
                                    AMOUNT to (-settledUp.groupOweOwed)
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return settledUpList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvGroupName: TextView = itemView.findViewById(R.id.tv_group_settled_up_name)
        val civGroupImage: CircleImageView = itemView.findViewById(R.id.civ_group_settled_up)
        val cvSettledUp: CardView = itemView.findViewById(R.id.cv_group_settled_up)
        val tvGetBackOrOwe: TextView = itemView.findViewById(R.id.tv_owe_get_back)
        val tvGetBackOrOweAmount: TextView = itemView.findViewById(R.id.tv_owe_get_back_amount)
    }
}