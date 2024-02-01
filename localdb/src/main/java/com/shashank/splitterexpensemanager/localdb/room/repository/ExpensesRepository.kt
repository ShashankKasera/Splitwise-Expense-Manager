package com.shashank.splitterexpensemanager.localdb.room.repository

import com.shashank.splitterexpensemanager.localdb.model.Expenses
import com.shashank.splitterexpensemanager.localdb.room.dao.ExpensesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExpensesRepository @Inject constructor(var expensesDao: ExpensesDao) {
    suspend fun insertExpenses(expenses: Expenses) = withContext(Dispatchers.IO) {
        expensesDao.insertExpenses(expenses)
    }
    suspend fun insertAllExpenses(vararg expenses: Expenses) = withContext(Dispatchers.IO) {
        expensesDao.insertAllExpenses(*expenses)
    }
    fun loadAllExpenses() = expensesDao.loadAllExpenses()
}