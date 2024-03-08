package com.shashank.splitterexpensemanager.feature.group

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.shortenName

class OweOwedGroupAdapter(
    private val oweOwedList: List<Pair<Person, Double>>
) : RecyclerView.Adapter<OweOwedGroupAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.owe_owed_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (person, amount) = oweOwedList[position]
        val context = holder.itemView.context
        with(holder) {
            when {
                amount > 0 -> {
                    tvName.text = context.getString(
                        R.string.owes_you,
                        (person.name)?.shortenName(person.name!!)
                    )
                    tvAmount.text = context.getString(R.string.rs, amount.formatNumber(2))
                    tvAmount.setTextColor(context.getColor(R.color.green))
                }

                amount < 0 -> {
                    tvName.text = context.getString(
                        R.string.you_owes_group_details,
                        (person.name)?.shortenName(person.name!!)
                    )
                    tvAmount.text = context.getString(R.string.rs, (-amount).formatNumber(2))
                    tvAmount.setTextColor(context.getColor(R.color.primary_dark))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (oweOwedList.size <= 3) oweOwedList.size else 2
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_owe_owed_name)
        val tvAmount: TextView = itemView.findViewById(R.id.tv_owe_owed_amount)
    }
}