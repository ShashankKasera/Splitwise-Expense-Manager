package com.shashank.splitterexpensemanager.feature.friendsetting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.GROUP_ID
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.model.Group

class GroupAdapter(
    private val actionProcessor: ActionProcessor,
    private val groupList: MutableList<Group>
) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.group_setting_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = groupList[position]

        with(holder) {
            tvName.text = group.groupName
            cvGroup.setOnClickListener {
                val groupId = group.id ?: -1
                actionProcessor.process(
                    ActionRequestSchema(
                        ActionType.GROUP_DETAILS.name,
                        hashMapOf(
                            GROUP_ID to groupId,
                        )
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_group_friend_setting_name)
        val cvGroup: CardView = itemView.findViewById(R.id.cv_group_friend_setting)
    }
}