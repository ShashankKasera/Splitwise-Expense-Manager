package com.shashank.splitterexpensemanager.feature.group

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.extension.formatNumber
import com.shashank.splitterexpensemanager.core.extension.gone
import com.shashank.splitterexpensemanager.core.extension.visible
import com.shashank.splitterexpensemanager.model.GroupAndOweOrOwed

class GroupAdapter(
    private val context: GroupFragment,
    private val groups: MutableList<GroupAndOweOrOwed>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {

    lateinit var oweOwedAdapter: OweOwedGroupAdapter
    private var oweOwedList = mutableListOf<Pair<Person, Double>>()

    interface OnItemClickListener {
        fun onItemClick(groupId: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.group_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val groupData = groups[position]
        with(holder) {
            tvGroupName.text = groupData.group.groupName
            val layoutParamsCv = holder.cvGroup.layoutParams
            layoutParamsCv.height = if (groupData.overall == 0.0) 300 else 550
            holder.cvGroup.layoutParams = layoutParamsCv
            if (groupData.hashMap.isEmpty()) {
                handleEmptyHashmapView(holder)
            } else {
                handleNonEmptyHashmapView(holder, groupData)
            }

            Glide.with(context).load(groupData.group.groupImage).into(ivGroupImage)

            clGroup.setOnClickListener {
                onItemClickListener.onItemClick(groupData.group.id ?: 0)
            }
        }
    }

    private fun handleEmptyHashmapView(holder: ViewHolder) {
        with(holder) {
            tvNoExpenses.visible()
            rvOweOwed.gone()
            tvOveOrOved.gone()
            tvPlus.gone()
        }
    }

    private fun handleNonEmptyHashmapView(holder: ViewHolder, groupData: GroupAndOweOrOwed) {
        with(holder) {
            tvNoExpenses.gone()
            tvOveOrOved.visible()
            rvOweOwed.visible()
            recyclerViewSetUp(holder)
            oweOwedList.clear()
            oweOwedList.addAll(groupData.hashMap.toList())
            oweOwedList.removeIf { it.second == 0.0 }
            oweOwedAdapter.notifyDataSetChanged()
            overall(holder, groupData.overall, oweOwedList.size)
            if (oweOwedList.size > 3) {
                tvPlus.visible()
                tvPlus.text = context.getString(R.string.plus_other_balance, (oweOwedList.size - 2))
            } else {
                tvPlus.gone()
            }
        }
    }

    private fun overall(holder: ViewHolder, overall: Double, size: Int) {
        with(holder) {
            when {
                size == 0 -> handleZeroSizeOverall(holder)
                size == 1 -> handleSingleSizeOverall(holder, overall)
                size > 1 -> handleMultipleSizeOverall(holder, overall)
                else -> handleEmptyOverall(holder)
            }
        }
    }

    private fun handleZeroSizeOverall(holder: ViewHolder) {
        with(holder) {
            tvOveOrOved.visible()
            tvOveOrOved.text = context.getString(R.string.you_settled_up)
            tvOveOrOved.setTextColor(context.resources.getColor(R.color.dark_grey))
        }
    }

    private fun handleSingleSizeOverall(holder: ViewHolder, overall: Double) {
        with(holder) {
            tvOveOrOved.visible()
            val layoutParamsCv = holder.cvGroup.layoutParams
            layoutParamsCv.height = 350
            holder.cvGroup.layoutParams = layoutParamsCv
            val colorResId = if (overall < 0) R.color.primary_dark else R.color.green
            val absOverall = Math.abs(overall)
            tvOveOrOved.text = context.getString(
                if (overall < 0) R.string.you_owe_rs else R.string.you_are_owed_rs,
                absOverall.formatNumber(2)
            )
            tvOveOrOved.setTextColor(context.resources.getColor(colorResId))
        }
    }

    private fun handleMultipleSizeOverall(holder: ViewHolder, overall: Double) {
        with(holder) {
            tvOveOrOved.visible()
            val colorResId = when {
                overall == 0.0 -> R.color.grey
                overall < 0 -> R.color.primary_dark
                else -> R.color.green
            }
            val absOverall = Math.abs(overall)
            tvOveOrOved.text = context.getString(
                if (overall == 0.0) {
                    R.string.you_settled_up
                } else if (overall < 0) {
                    R.string.you_owe_rs
                } else {
                    R.string.you_are_owed_rs
                },
                absOverall.formatNumber(2)
            )
            tvOveOrOved.setTextColor(context.resources.getColor(colorResId))
        }
    }

    private fun handleEmptyOverall(holder: ViewHolder) {
        with(holder) {
            tvOveOrOved.gone()
        }
    }

    override fun getItemCount(): Int {
        return groups.size
    }

    private fun recyclerViewSetUp(holder: ViewHolder) {
        oweOwedAdapter = OweOwedGroupAdapter(oweOwedList)
        holder.rvOweOwed.layoutManager = LinearLayoutManager(context.context)
        holder.rvOweOwed.adapter = oweOwedAdapter
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val clGroup: ConstraintLayout = itemView.findViewById(R.id.cl_group)
        val cvGroup: CardView = itemView.findViewById(R.id.cv_group_image)
        val tvGroupName: TextView = itemView.findViewById(R.id.tv_group_name)
        val ivGroupImage: ImageView = itemView.findViewById(R.id.iv_group_icon)
        val tvNoExpenses: TextView = itemView.findViewById(R.id.tv_no_expenses)
        val tvOveOrOved: TextView = itemView.findViewById(R.id.tv_ove_or_oved)
        val tvPlus: TextView = itemView.findViewById(R.id.tv_plus)
        val rvOweOwed: RecyclerView = itemView.findViewById(R.id.rv_owe_owed_group)
    }
}