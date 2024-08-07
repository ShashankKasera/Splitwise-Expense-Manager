package com.shashank.splitterexpensemanager.feature.settleup

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.AMOUNT
import com.shashank.splitterexpensemanager.core.CommonImages
import com.shashank.splitterexpensemanager.core.FEMALE
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.MALE
import com.shashank.splitterexpensemanager.core.PAYER_ID
import com.shashank.splitterexpensemanager.core.RECEIVER_ID
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import de.hdodenhof.circleimageview.CircleImageView

class SettleUpAdapter(
    private val groupId: Long,
    private val personId: Long,
    private val actionProcessor: ActionProcessor,
    private val personList: List<Pair<Person, Double>>
) : RecyclerView.Adapter<SettleUpAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.group_member_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (person, amount) = personList[position]
        val context = holder.itemView.context

        holder.tvName.text = person.name

        with(holder) {
            tvName.maxLines = 1
            tvName.ellipsize = TextUtils.TruncateAt.END
            when {
                amount > 0 -> {
                    tvGetBackOrOwe.text = context.getString(R.string.you_are_owed)
                    tvGetBackOrOweAmount.text =
                        context.getString(R.string.rs, (amount).formatNumber(2))
                    tvGetBackOrOwe.setTextColor(context.getColor(R.color.green))
                    tvGetBackOrOweAmount.setTextColor(context.getColor(R.color.green))
                }

                amount < 0 -> {
                    tvGetBackOrOwe.text = context.getString(R.string.you_owed)
                    tvGetBackOrOweAmount.text =
                        context.getString(R.string.rs, (-amount).formatNumber(2))
                    tvGetBackOrOwe.setTextColor(context.getColor(R.color.primary_dark))
                    tvGetBackOrOweAmount.setTextColor(context.getColor(R.color.primary_dark))
                }
            }
            if (person.gender == MALE) {
                Glide.with(context).load(CommonImages.USER_ICON).into(holder.civGroupMember)
            } else if (person.gender == FEMALE) {
                Glide.with(context).load(CommonImages.GIRL).into(holder.civGroupMember)
            }
            cvGroupMember.setOnClickListener {
                when {
                    amount > 0 -> {
                        actionProcessor.process(
                            ActionRequestSchema(
                                ActionType.ADD_PAYMENT.name,
                                hashMapOf(
                                    PAYER_ID to (person.id ?: -1),
                                    RECEIVER_ID to (personId),
                                    GROUP_ID to (groupId),
                                    AMOUNT to (amount),
                                )
                            )
                        )
                    }

                    amount < 0 -> {
                        actionProcessor.process(
                            ActionRequestSchema(
                                ActionType.ADD_PAYMENT.name,
                                hashMapOf(
                                    PAYER_ID to (personId),
                                    RECEIVER_ID to (person.id ?: -1),
                                    GROUP_ID to (groupId),
                                    AMOUNT to (-amount)
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_group_member_name)
        val cvGroupMember: CardView = itemView.findViewById(R.id.cv_add_group_member)
        val tvGetBackOrOwe: TextView = itemView.findViewById(R.id.tv_get_back)
        val tvGetBackOrOweAmount: TextView = itemView.findViewById(R.id.tv_get_back_amount)
        val civGroupMember: CircleImageView = itemView.findViewById(R.id.civ_group_member)
    }
}