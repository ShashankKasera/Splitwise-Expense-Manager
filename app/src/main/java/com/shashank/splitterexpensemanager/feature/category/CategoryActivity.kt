package com.shashank.splitterexpensemanager.feature.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.localdb.model.Category
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    private var groupTypeList = mutableListOf<Category>()
    private val viewModel: CategoryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        recyclerView = findViewById(R.id.rv_category)
        lifecycleScope.launch {
            viewModel.allCategoryLiveData.observe(this@CategoryActivity) {
                groupTypeList.addAll(it)
            }
        }
        val categoryAdapter = CategoryAdapter(groupTypeList)
        recyclerView.layoutManager = LinearLayoutManager(this@CategoryActivity)
        recyclerView.adapter = categoryAdapter
    }
}