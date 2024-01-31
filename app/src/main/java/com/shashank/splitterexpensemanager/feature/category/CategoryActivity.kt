package com.shashank.splitterexpensemanager.feature.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    private val viewModel: CategoryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        lifecycleScope.launch {
            viewModel.insertAllCategory(
                Category(null, "Home", "imahe"),
                Category(null, "stor", "imahe"),
                Category(null, "car", "imahe"),
                Category(null, "game", "imahe"),
                Category(null, "food", "imahe"),
                Category(null, "mobile", "imahe")
            )

            viewModel.categoryLiveData.observe(this@CategoryActivity) {
                Log.i("gyug", "onCreate: $it")
            }
        }

        recyclerView = findViewById(R.id.rv_category)
        val categoryAdapter = CategoryAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = categoryAdapter
    }
}