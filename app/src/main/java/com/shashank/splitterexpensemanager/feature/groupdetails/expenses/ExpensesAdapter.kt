package com.shashank.splitterexpensemanager.feature.groupdetails.expenses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.EXPENSES_ID
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPerson
import de.hdodenhof.circleimageview.CircleImageView

class ExpensesAdapter(
    private var actionProcessor: ActionProcessor,
    private val personId: Long,
    private val expensesList: List<ExpenseWithCategoryAndPerson?>
) : RecyclerView.Adapter<ExpensesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.expenses_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val expenseItem = expensesList[position]
        val context = holder.itemView.context

        with(holder) {
            tvName.text = expenseItem?.person?.name
            tvAmount.text = expenseItem?.expense?.amount.toString()
            tvDate.text = expenseItem?.expense?.date
            tvTime.text = expenseItem?.expense?.time

            clExpenses.setOnClickListener {
                actionProcessor.process(
                    ActionRequestSchema(
                        ActionType.EXPENSES_DETAILS.name,
                        hashMapOf(
                            EXPENSES_ID to (expenseItem?.expense?.id ?: -1),
                            GROUP_ID to (expenseItem?.expense?.groupId ?: -1)
                        )
                    )
                )
            }
            if (!expenseItem?.expense?.description.isNullOrEmpty()) {
                tvDescription.visible()
                tvDescription.text = expenseItem?.expense?.description
            }

            val categoryImage = expenseItem?.category?.categoryImage
            if (categoryImage != null && categoryImage != 0) {
                civCategory.setImageResource(categoryImage)
            }

            val isPersonIdMatch = personId == expenseItem?.person?.id
            with(tvBorrowed) {
                text =
                    context.getString(if (isPersonIdMatch) R.string.you_lent else R.string.you_borrowed)
                setTextColor(context.resources.getColor(if (isPersonIdMatch) R.color.green else R.color.primary_dark))
            }

            with(tvBorrowedAmount) {
                setTextColor(context.resources.getColor(if (isPersonIdMatch) R.color.green else R.color.primary_dark))

                val amount = expenseItem?.expense?.amount ?: 0.0
                val splitAmount = expenseItem?.expense?.splitAmount ?: 0.0
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
        return expensesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_persian_name)
        val tvAmount: TextView = itemView.findViewById(R.id.tv_paid_amount)
        val tvDate: TextView = itemView.findViewById(R.id.tv_date_expenses)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time_expenses)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        val tvBorrowed: TextView = itemView.findViewById(R.id.tv_you_borrowed)
        val tvBorrowedAmount: TextView = itemView.findViewById(R.id.tv_you_borrowed_amount)
        val civCategory: CircleImageView = itemView.findViewById(R.id.civ_category)
        val clExpenses: ConstraintLayout = itemView.findViewById(R.id.cl_expense)
    }
}