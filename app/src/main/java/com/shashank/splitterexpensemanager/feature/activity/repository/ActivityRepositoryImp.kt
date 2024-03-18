package com.shashank.splitterexpensemanager.feature.activity.repository

import com.shashank.splitterexpensemanager.localdb.room.dao.ExpensesDao
import com.shashank.splitterexpensemanager.localdb.room.dao.RepayDao
import com.shashank.splitterexpensemanager.mapper.expensewithcategoryandpersonandgroupmapper.ExpenseWithCategoryAndPersonAndGroupListMapper
import com.shashank.splitterexpensemanager.mapper.repaywithpersonandgroupmapper.RepayWithPersonAndGroupListMapper
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ActivityRepositoryImp @Inject constructor(
    private val expensesDao: ExpensesDao,
    private val repayDao: RepayDao,
    private val expenseWithCategoryAndPersonAndGroupListMapper: ExpenseWithCategoryAndPersonAndGroupListMapper,
    private val repayWithPersonAndGroupListMapper: RepayWithPersonAndGroupListMapper,
) : ActivityRepository {
    override fun loadAllExpenses() = expensesDao.loadAllExpenses().map {
        expenseWithCategoryAndPersonAndGroupListMapper.map(it)
    }

    override fun loadAllRepay() = repayDao.loadAllRepay().map {
        repayWithPersonAndGroupListMapper.map(it)
    }
}