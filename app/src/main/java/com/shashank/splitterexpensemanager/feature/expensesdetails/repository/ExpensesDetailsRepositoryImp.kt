package com.shashank.splitterexpensemanager.feature.expensesdetails.repository

import com.shashank.splitterexpensemanager.localdb.room.dao.ExpensesDao
import com.shashank.splitterexpensemanager.localdb.room.dao.OweOrOwedDao
import com.shashank.splitterexpensemanager.mapper.expensecategorypersonmapper.ExpenseWithCategoryAndPersonMapper
import com.shashank.splitterexpensemanager.mapper.oweorowedwitpersonmapper.OweOrOwedWithPersonListMapper
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExpensesDetailsRepositoryImp @Inject constructor(
    private val expensesDao: ExpensesDao,
    private val oweOrOwedDao: OweOrOwedDao,
    private val expenseWithCategoryAndPersonMapper: ExpenseWithCategoryAndPersonMapper,
    private val oweOrOwedWithPersonListMapper: OweOrOwedWithPersonListMapper,
) : ExpensesDetailsRepository {
    override fun loadExpensesByExpensesId(expensesId: Long) =
        expensesDao.loadExpensesByExpensesId(expensesId).map {
            expenseWithCategoryAndPersonMapper.map(it)
        }

    override fun loadAllOweOrOwedByExpensesId(expensesId: Long) =
        oweOrOwedDao.loadAllOweOwedByExpensesId(expensesId).map {
            oweOrOwedWithPersonListMapper.map(it)
        }
}