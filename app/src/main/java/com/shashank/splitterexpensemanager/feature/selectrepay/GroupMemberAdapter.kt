package com.shashank.splitterexpensemanager.feature.selectrepay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.core.CommonImages
import de.hdodenhof.circleimageview.CircleImageView

class GroupMemberAdapter(
    private var selectPayer: Int,
    private var selectRecipient: Int,
    private val person: List<Person>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<GroupMemberAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int, data: Person, checked: Boolean)
    }

    fun selectPayerPosition(selectPosition: Int) {
        this.selectPayer = selectPosition
        notifyDataSetChanged()
    }

    fun selectRecipientPosition(selectPosition: Int) {
        this.selectRecipient = selectPosition
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context

        holder.tvName.text = person[position].name
        holder.tvNumber.text = person[position].number

        holder.checkBox.isChecked = selectPayer == position || selectRecipient == position

        holder.checkBox.setOnClickListener {
            onItemClickListener.onItemClick(position, person[position], holder.checkBox.isChecked)
        }
        Glide.with(context).load(CommonImages.USER_ICON).into(holder.civGroupMember)
    }

    override fun getItemCount(): Int {
        return person.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_user_name)
        val tvNumber: TextView = itemView.findViewById(R.id.tv_user_number)
        val cvGroupMember: CardView = itemView.findViewById(R.id.cv_user)
        val checkBox: CheckBox = itemView.findViewById(R.id.cb_add_friends)
        val civGroupMember: CircleImageView = itemView.findViewById(R.id.civ_user)
    }
}