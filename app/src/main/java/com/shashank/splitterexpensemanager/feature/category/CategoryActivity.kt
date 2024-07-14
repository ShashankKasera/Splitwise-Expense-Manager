package com.shashank.splitterexpensemanager.feature.category

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.CATEGORY
import com.shashank.splitterexpensemanager.model.Category
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    private var categoryList = mutableListOf<Category>()
    private val viewModel: CategoryViewModel by viewModels()
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var toolbar: TextView
    lateinit var ivBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        init()
        setUpRecyclerView()
        getCategory()
    }

    private fun init() {
        recyclerView = findViewById(R.id.rv_category)
        toolbar = findViewById(R.id.tv_tb_category)
        ivBack = findViewById(R.id.iv_tb_category)

        toolbar.text = getString(R.string.category)
        ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setUpRecyclerView() {
        categoryAdapter = CategoryAdapter(
            this,
            categoryList,
            object : CategoryAdapter.OnItemClickListener {
                override fun onItemClick(position: Int, data: Category) {
                    val returnIntent = Intent()
                    returnIntent.putExtra(CATEGORY, data)
                    setResult(RESULT_OK, returnIntent)
                    finish()
                }
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(this@CategoryActivity)
        recyclerView.adapter = categoryAdapter
    }

    private fun getCategory() {
        viewModel.allCategory()
        lifecycleScope.launch {
            viewModel.allCategory.collect {
                categoryList.addAll(it)
                categoryAdapter.notifyDataSetChanged()
            }
        }
    }
}