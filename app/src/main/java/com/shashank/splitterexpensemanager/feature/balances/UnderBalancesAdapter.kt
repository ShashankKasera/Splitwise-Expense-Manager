package com.shashank.splitterexpensemanager.feature.balances

import android.util.Log
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
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.PAYER_ID
import com.shashank.splitterexpensemanager.core.RECEIVER_ID
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import de.hdodenhof.circleimageview.CircleImageView

class UnderBalancesAdapter(
    private val uPerson: Person,
    private val groupId: Long,
    private val personId: Long,
    private val actionProcessor: ActionProcessor,
    private val underBalancesList: List<Pair<Person, Double>>
) : RecyclerView.Adapter<UnderBalancesAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(data: Person)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.under_balances_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (person, amount) = underBalancesList[position]
        val context = holder.itemView.context
        Glide.with(context).load(CommonImages.USER_ICON).into(holder.civFriendImage)
        if (amount > 0) {
            with(holder) {
                tvOwedPerson.text = uPerson.name
                tvOwePerson.text = person.name
                tvAmount.text = context.getString(R.string.rs, amount.formatNumber(2))
                tvAmount.setTextColor(context.getColor(R.color.green))
            }
        } else if (amount < 0) {
            with(holder) {
                tvOwePerson.text = uPerson.name
                tvOwedPerson.text = person.name
                tvAmount.text = context.getString(R.string.rs, (-amount).formatNumber(2))
                tvAmount.setTextColor(context.getColor(R.color.primary_dark))
            }
        }

        holder.cvSettleUp.setOnClickListener {
            when {
                amount > 0 -> {
                    Log.i("efhfhk", "onBindViewHolder: + ${person.id ?: -1} $personId ")

                    actionProcessor.process(
                        ActionRequestSchema(
                            ActionType.ADD_PAYMENT.name,
                            hashMapOf(
                                PAYER_ID to (person.id ?: -1),
                                RECEIVER_ID to (uPerson.id ?: -1),
                                GROUP_ID to (groupId),
                                AMOUNT to (amount),
                            )
                        )
                    )
                }

                amount < 0 -> {
                    Log.i("efhfhk", "onBindViewHolder: - $personId ${person.id ?: -1}")
                    actionProcessor.process(
                        ActionRequestSchema(
                            ActionType.ADD_PAYMENT.name,
                            hashMapOf(
                                PAYER_ID to (uPerson.id ?: -1),
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


    override fun getItemCount(): Int {
        return underBalancesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvOwePerson: TextView = itemView.findViewById(R.id.tv_owe_name_under_balances)
        val tvOwedPerson: TextView = itemView.findViewById(R.id.tv_owed_name_under_balances)
        val tvAmount: TextView = itemView.findViewById(R.id.tv_amount_under_balances)
        val civFriendImage: CircleImageView = itemView.findViewById(R.id.civ_balancer_under_image)
        val cvSettleUp: CardView = itemView.findViewById(R.id.cv_settle_up)
    }
}