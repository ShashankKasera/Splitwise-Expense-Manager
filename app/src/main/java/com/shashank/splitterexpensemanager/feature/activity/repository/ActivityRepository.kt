package com.shashank.splitterexpensemanager.feature.activity.repository

import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPersonAndGroup
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {
    fun loadAllExpenses(): Flow<List<ExpenseWithCategoryAndPersonAndGroup>>
}