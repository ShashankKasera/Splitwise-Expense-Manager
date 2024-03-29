package com.example.splitwiseexpensemanager.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.splitwiseexpensemanager.R

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("reghj", "onBindViewHolder: ")
    }
    override fun getItemCount(): Int {
        return 10
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val civCategoryImage: ImageView = itemView.findViewById(R.id.civ_category_image)
        val tvCategoryName: TextView = itemView.findViewById(R.id.tv_category_name)
    }
}