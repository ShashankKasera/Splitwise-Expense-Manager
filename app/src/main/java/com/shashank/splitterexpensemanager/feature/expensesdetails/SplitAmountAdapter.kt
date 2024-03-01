package com.shashank.splitterexpensemanager.feature.expensesdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.model.OweOrOwedWithPerson

class SplitAmountAdapter(
    private val context: ExpensesDetailsActivity,
    private val personId: Long,
    private val oweOwedList: MutableList<OweOrOwedWithPerson>
) : RecyclerView.Adapter<SplitAmountAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.amount_split_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = if (personId == oweOwedList[position].personOwed.id) {
            context.getString(R.string.you_owes)
        } else {
            context.getString(R.string.owes, oweOwedList[position].personOwed.name)
        }
        holder.tvAmount.text =
            context.getString(R.string.rs, (oweOwedList[position].oweOrOwed.amount).formatNumber(2))
    }

    override fun getItemCount(): Int {
        return oweOwedList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_splitter_name)
        val tvAmount: TextView = itemView.findViewById(R.id.tv_splitter_owe)
    }
}