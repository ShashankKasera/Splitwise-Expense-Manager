package com.shashank.splitterexpensemanager.feature.balances

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.CommonImages
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.model.Balances
import de.hdodenhof.circleimageview.CircleImageView

class BalancesAdapter(
    private val actionProcessor: ActionProcessor,
    private val groupId: Long,
    private val personId: Long,
    private val balances: MutableList<Balances>,
) : RecyclerView.Adapter<BalancesAdapter.ViewHolder>() {


    interface OnItemClickListener {
        fun onItemClick(data: Person)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.balances_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val balances = balances[position]
        val context = holder.itemView.context

        setUpRecycleView(context, holder, balances.person, balances.oweOwedList)
        setVisibility(holder, balances.amount)
        setData(context, holder, balances.amount, balances.person.name)
        Glide.with(context).load(CommonImages.USER_ICON).into(holder.civImage)
    }

    private fun setUpRecycleView(
        context: Context,
        holder: ViewHolder,
        person: Person,
        oweOwedList: List<Pair<Person, Double>>
    ) {
        val underBalancesAdapter =
            UnderBalancesAdapter(person, groupId, personId, actionProcessor, oweOwedList)
        holder.rvUnderBalances.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = underBalancesAdapter
        }
    }

    private fun setVisibility(holder: ViewHolder, amount: Double) {
        with(holder) {
            if (amount != 0.0) {
                tvGetBackOrOwe.visible()
                tvGetBackOrOweAmount.visible()
                tvInTotal.visible()
                ivMoreOpen.visible()
                tvSettledUp.gone()
            } else {
                tvGetBackOrOwe.gone()
                tvGetBackOrOweAmount.gone()
                tvInTotal.gone()
                ivMoreOpen.gone()
                tvSettledUp.visible()
            }
            ivMoreOpen.setOnClickListener {
                rvUnderBalances.visible()
                ivMoreClose.visible()
                ivMoreOpen.gone()
            }

            ivMoreClose.setOnClickListener {
                rvUnderBalances.gone()
                ivMoreClose.gone()
                ivMoreOpen.visible()
            }
        }
    }

    private fun setData(context: Context, holder: ViewHolder, amount: Double, name: String?) {
        with(holder) {
            holder.tvName.text = name
            when {
                amount > 0 -> {
                    tvGetBackOrOwe.text = context.getString(R.string.get_back)
                    tvGetBackOrOweAmount.text =
                        context.getString(R.string.rs, amount.formatNumber(2))
                    tvGetBackOrOweAmount.setTextColor(context.getColor(R.color.green))
                }

                amount < 0 -> {
                    tvGetBackOrOwe.text = context.getString(R.string.owes_group_member)
                    tvGetBackOrOweAmount.text =
                        context.getString(R.string.rs, (-amount).formatNumber(2))
                    tvGetBackOrOweAmount.setTextColor(context.getColor(R.color.primary_dark))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return balances.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name_balance)
        val tvGetBackOrOwe: TextView = itemView.findViewById(R.id.tv_owe_or_get_back_balance)
        val tvGetBackOrOweAmount: TextView =
            itemView.findViewById(R.id.tv_amount_owe_or_get_back_balances)
        val tvInTotal: TextView = itemView.findViewById(R.id.tv_in_total_balances)
        val tvSettledUp: TextView = itemView.findViewById(R.id.tv_settled_up_balance)
        val ivMoreOpen: ImageView = itemView.findViewById(R.id.iv_more_open_balance)
        val ivMoreClose: ImageView = itemView.findViewById(R.id.iv_more_close_balance)
        val rvUnderBalances: RecyclerView = itemView.findViewById(R.id.rv_under_balances)
        val civImage: CircleImageView = itemView.findViewById(R.id.civ_person_image_balancer)
    }
}