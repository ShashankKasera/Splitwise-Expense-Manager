package com.shashank.splitterexpensemanager.feature.activity.repository

import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPersonAndGroup
import com.shashank.splitterexpensemanager.model.RepayWithPersonAndGroup
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {
    fun loadAllExpenses(): Flow<List<ExpenseWithCategoryAndPersonAndGroup>>

    fun loadAllRepay(): Flow<List<RepayWithPersonAndGroup>>
}