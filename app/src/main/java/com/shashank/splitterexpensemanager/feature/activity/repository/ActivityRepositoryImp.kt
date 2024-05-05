package com.shashank.splitterexpensemanager.feature.activity.repository

import com.shashank.splitterexpensemanager.localdb.room.dao.ExpensesDao
import com.shashank.splitterexpensemanager.mapper.expensewithcategoryandpersonandgroupmapper.ExpenseWithCategoryAndPersonAndGroupListMapper
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ActivityRepositoryImp @Inject constructor(
    private val expensesDao: ExpensesDao,
    private val expenseWithCategoryAndPersonAndGroupListMapper: ExpenseWithCategoryAndPersonAndGroupListMapper,
) : ActivityRepository {
    override fun loadAllExpenses() = expensesDao.loadAllExpenses().map {
        expenseWithCategoryAndPersonAndGroupListMapper.map(it)
    }
}