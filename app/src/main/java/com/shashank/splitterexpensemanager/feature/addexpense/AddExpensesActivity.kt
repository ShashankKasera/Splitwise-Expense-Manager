package com.shashank.splitterexpensemanager.feature.addexpense

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddExpensesActivity : AppCompatActivity() {

    @Inject
    lateinit var actionProcessor: ActionProcessor
    lateinit var tvCategory: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        tvCategory = findViewById(R.id.cl_category_expenses)

        tvCategory.setOnClickListener {
            actionProcessor.process(
                ActionRequestSchema(
                    ActionType.CATEGORY.name
                )
            )
        }
    }
}