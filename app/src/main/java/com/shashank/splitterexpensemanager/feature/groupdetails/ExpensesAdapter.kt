package com.shashank.splitterexpensemanager.feature.groupdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPerson
import de.hdodenhof.circleimageview.CircleImageView

class ExpensesAdapter(
    private val personId: Long,
    private val expensesList: List<ExpenseWithCategoryAndPerson?>
) : RecyclerView.Adapter<ExpensesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = expensesList[position]?.person?.name
        holder.tvAmount.text = expensesList[position]?.expense?.amount.toString()
        holder.tvDate.text = expensesList[position]?.expense?.date
        holder.tvTime.text = expensesList[position]?.expense?.time

        if (!expensesList[position]?.expense?.description!!.isEmpty()) {
            holder.tvDescription.visible()
            holder.tvDescription.text = expensesList[position]?.expense?.description
        }
        if (!expensesList[position]?.category?.categoryImage.toString().isEmpty()) {
            holder.civCategory.setImageResource(expensesList[position]?.category!!.categoryImage)
        }
        if (personId == expensesList[position]?.person?.id) {
            holder.tvBorrowed.text = "You Lent"
            holder.tvBorrowed.setTextColor(
                holder.tvBorrowed.context.getResources().getColor(R.color.green)
            )
            holder.tvBorrowedAmount.setTextColor(
                holder.tvBorrowedAmount.context.getResources().getColor(R.color.green)
            )
            val amount = expensesList[position]?.expense?.amount ?: 0.0
            val splitAmount = expensesList[position]?.expense?.splitAmount ?: 0.0
            val lentAmount = (amount - splitAmount)

            holder.tvBorrowedAmount.text = lentAmount.formatNumber(2)
        } else {
            holder.tvBorrowed.setTextColor(
                holder.tvBorrowed.context.getResources().getColor(R.color.primary_dark)
            )
            holder.tvBorrowedAmount.text =
                expensesList[position]?.expense?.splitAmount?.formatNumber(2)
        }
    }

    override fun getItemCount(): Int {
        return expensesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_persian_name)
        val tvAmount: TextView = itemView.findViewById(R.id.tv_paid_amount)
        val tvDate: TextView = itemView.findViewById(R.id.tv_date_activity)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time_activity)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        val tvBorrowed: TextView = itemView.findViewById(R.id.tv_you_borrowed)
        val tvBorrowedAmount: TextView = itemView.findViewById(R.id.tv_you_borrowed_amount)
        val civCategory: CircleImageView = itemView.findViewById(R.id.civ_category)
    }
}