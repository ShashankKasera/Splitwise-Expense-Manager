package com.shashank.splitterexpensemanager.feature.activity.allrepay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.model.RepayWithPersonAndGroup

class AllRepayAdapter(
    private val repayList: List<RepayWithPersonAndGroup?>
) : RecyclerView.Adapter<AllRepayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.repay_activity_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repayItem = repayList[position]
        val context = holder.itemView.context

        with(holder) {
            tvGroupName.text = repayItem?.group?.groupName
            tvPayerName.text = repayItem?.payer?.name
            tvReceiverName.text = repayItem?.receiver?.name
            tvAmount.text = repayItem?.repay?.amount?.formatNumber(2)
            tvDate.text = repayItem?.repay?.date
//            tvDate.text = repayItem?.repay?.date?.dateToString((repayItem.repay.date),"dd/mm/yyyy")
            tvTime.text = repayItem?.repay?.time


            if (!repayItem?.repay?.description.isNullOrEmpty()) {
                tvDescription.visible()
                tvDescription.text = repayItem?.repay?.description
            }
        }
    }

    override fun getItemCount(): Int {
        return repayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvGroupName: TextView = itemView.findViewById(R.id.tv_group_name_repay_activity)
        val tvPayerName: TextView = itemView.findViewById(R.id.tv_payer_name_repay_activity)
        val tvReceiverName: TextView = itemView.findViewById(R.id.tv_receiver_name_repay_activity)
        val tvAmount: TextView = itemView.findViewById(R.id.tv_amount_repay_activity)
        val tvDate: TextView = itemView.findViewById(R.id.tv_date_repay_activity)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time_repay_activity)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description_repay_activity)
        val cvRepay: CardView = itemView.findViewById(R.id.cv_activity_repay)
    }
}