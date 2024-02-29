package com.shashank.splitterexpensemanager.feature.expensesdetails.repository

import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPerson
import com.shashank.splitterexpensemanager.model.OweOrOwedWithPerson
import kotlinx.coroutines.flow.Flow

interface ExpensesDetailsRepository {

    suspend fun deleteExpenses(expensesId: Long)
    fun loadExpensesByExpensesId(expensesId: Long): Flow<ExpenseWithCategoryAndPerson>
    fun loadAllOweOrOwedByExpensesId(expensesId: Long): Flow<List<OweOrOwedWithPerson>>
}