package com.shashank.splitterexpensemanager.feature.groupdetails

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.extension.EMPTY
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.shortenName

class OweOwedAdapter(
    private val expensesList: List<Pair<Person, Double>>
) : RecyclerView.Adapter<OweOwedAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.owe_owed_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (person, amount) = expensesList[position]
        val context = holder.itemView.context

        with(holder) {
            tvName.maxLines = 1
            tvName.ellipsize = TextUtils.TruncateAt.END
            when {
                amount > 0 -> {
                    tvName.text = context.getString(
                        R.string.owes_you,
                        person.name?.shortenName(person.name ?: String.EMPTY)
                    )
                    tvAmount.text = context.getString(R.string.rs, amount.formatNumber(2))
                    tvAmount.setTextColor(context.getColor(R.color.green))
                }

                amount < 0 -> {
                    tvName.text = context.getString(
                        R.string.you_owes_group_details,
                        person.name?.shortenName(person.name ?: String.EMPTY)
                    )
                    tvAmount.text = context.getString(R.string.rs, (-amount).formatNumber(2))
                    tvAmount.setTextColor(context.getColor(R.color.primary_dark))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (expensesList.size > 2) 2 else expensesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_owe_owed_name)
        val tvAmount: TextView = itemView.findViewById(R.id.tv_owe_owed_amount)
    }
}