package com.shashank.splitterexpensemanager.feature.groupdetails.repay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.REPAY_ID
import com.shashank.splitterexpensemanager.core.TotalImages
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.model.RepayWithPerson
import de.hdodenhof.circleimageview.CircleImageView

class RepayAdapter(
    private val actionProcessor: ActionProcessor,
    private val repayList: List<RepayWithPerson?>
) : RecyclerView.Adapter<RepayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.repay_group_details_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repayItem = repayList[position]
        val context = holder.itemView.context

        with(holder) {
            tvPayerName.text = repayItem?.payer?.name
            tvReceiverName.text = repayItem?.receiver?.name
            tvAmount.text = repayItem?.repay?.amount?.formatNumber(2)
            Glide.with(context).load(TotalImages.TOTAL_YOU_PAID_FOR).into(civRepay)
            tvDate.text = repayItem?.repay?.date
            tvTime.text = repayItem?.repay?.time


            if (!repayItem?.repay?.description.isNullOrEmpty()) {
                tvDescription.visible()
                tvDescription.text = repayItem?.repay?.description
            }

            cvRepay.setOnClickListener {
                actionProcessor.process(
                    ActionRequestSchema(
                        ActionType.REPAY_DETAILS.name,
                        hashMapOf(
                            REPAY_ID to (repayItem?.repay?.id ?: -1),
                            GROUP_ID to (repayItem?.repay?.groupId ?: -1)
                        )
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return repayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPayerName: TextView = itemView.findViewById(R.id.tv_payer_name)
        val tvReceiverName: TextView = itemView.findViewById(R.id.tv_receiver_name_repay)
        val civRepay: CircleImageView = itemView.findViewById(R.id.civ_repay)
        val tvAmount: TextView = itemView.findViewById(R.id.tv_amount_repay)
        val tvDate: TextView = itemView.findViewById(R.id.tv_date_repay)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time_repay)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description_repay)
        val cvRepay: CardView = itemView.findViewById(R.id.cv_group_repay)
    }
}