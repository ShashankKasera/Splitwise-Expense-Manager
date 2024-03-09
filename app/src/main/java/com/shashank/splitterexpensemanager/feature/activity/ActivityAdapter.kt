package com.shashank.splitterexpensemanager.feature.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPersonAndGroup

class ActivityAdapter(
    private val personId: Long,
    private val activityList: MutableList<ExpenseWithCategoryAndPersonAndGroup>
) : RecyclerView.Adapter<ActivityAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val activity = activityList[position]
        with(holder) {
            tvGroupName.text = activity.group.groupName
            tvName.text = activity.person.name
            tvAmount.text = activity.expense.amount.toString()
            tvDate.text = activity.expense.date
            tvTime.text = activity.expense.time
            tvTime.text = activity.expense.time

            if (activity.expense.description.isNotEmpty()) {
                tvDescription.visible()
                tvDescription.text = activity.expense.description
            }

            val categoryImage = activity.category.categoryImage
            if (categoryImage != 0) {
                civCategory.setImageResource(categoryImage)
            }

            val isPersonIdMatch = personId == activity.person.id
            with(tvBorrowed) {
                text =
                    context.getString(if (isPersonIdMatch) R.string.you_lent else R.string.you_borrowed)
                setTextColor(context.resources.getColor(if (isPersonIdMatch) R.color.green else R.color.primary_dark))
            }

            with(tvBorrowedAmount) {
                setTextColor(context.resources.getColor(if (isPersonIdMatch) R.color.green else R.color.primary_dark))

                val amount = activity.expense.amount
                val splitAmount = activity.expense.splitAmount
                text =
                    if (isPersonIdMatch) {
                        (amount - splitAmount).formatNumber(2)
                    } else {
                        splitAmount.formatNumber(2)
                    }
            }
        }
    }

    override fun getItemCount(): Int {
        return activityList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val civCategory: ImageView = itemView.findViewById(R.id.civ_category)
        val tvGroupName: TextView = itemView.findViewById(R.id.tv_group_name)
        val tvName: TextView = itemView.findViewById(R.id.tv_persian_name)
        val tvAmount: TextView = itemView.findViewById(R.id.tv_paid_amount)
        val tvDate: TextView = itemView.findViewById(R.id.tv_date_activity)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time_activity)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        val tvBorrowed: TextView = itemView.findViewById(R.id.tv_you_borrowed)
        val tvBorrowedAmount: TextView = itemView.findViewById(R.id.tv_you_borrowed_amount)
    }
}