package com.shashank.splitterexpensemanager.feature.balances

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.model.Balances

class BalancesAdapter(
    private val balancesActivity: BalancesActivity,
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

        setUpRecycleView(holder, balances.person.name, balances.oweOwedList)
        setVisibility(holder, balances.amount)
        setData(context, holder, balances.amount, balances.person.name)
    }

    private fun setUpRecycleView(
        holder: ViewHolder,
        name: String?,
        oweOwedList: List<Pair<Person, Double>>
    ) {
        val underBalancesAdapter = UnderBalancesAdapter(name, oweOwedList)
        holder.rvUnderBalances.apply {
            layoutManager = LinearLayoutManager(balancesActivity)
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
    }
}