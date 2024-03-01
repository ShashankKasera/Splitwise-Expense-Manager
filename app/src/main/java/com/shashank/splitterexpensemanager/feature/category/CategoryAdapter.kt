package com.shashank.splitterexpensemanager.feature.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.model.Category

class CategoryAdapter(
    private val categories: List<Category>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int, data: Category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvCategoryName.text = categories[position].categoryName
        holder.civCategoryImage.setImageResource(categories[position].categoryImage)
        holder.clCategory.setOnClickListener {
            onItemClickListener.onItemClick(position, categories[position])
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val civCategoryImage: ImageView = itemView.findViewById(R.id.civ_category_image)
        val tvCategoryName: TextView = itemView.findViewById(R.id.tv_category_name)
        val clCategory: ConstraintLayout = itemView.findViewById(R.id.cl_category)
    }
}