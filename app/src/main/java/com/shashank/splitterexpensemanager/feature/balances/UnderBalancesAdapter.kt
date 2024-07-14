package com.shashank.splitterexpensemanager.feature.balances

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.extension.formatNumber

class UnderBalancesAdapter(
    private val name: String?,
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

        if (amount > 0) {
            with(holder) {
                tvOwedPerson.text = name
                tvOwePerson.text = person.name
                tvAmount.text = context.getString(R.string.rs, amount.formatNumber(2))
                tvAmount.setTextColor(context.getColor(R.color.green))
            }
        } else if (amount < 0) {
            with(holder) {
                tvOwePerson.text = name
                tvOwedPerson.text = person.name
                tvAmount.text = context.getString(R.string.rs, (-amount).formatNumber(2))
                tvAmount.setTextColor(context.getColor(R.color.primary_dark))
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
    }
}