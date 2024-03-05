package com.shashank.splitterexpensemanager.feature.addexpense.repository

import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPerson
import com.shashank.splitterexpensemanager.localdb.model.Expenses as ExpensesEntity
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwed as OweOrOwedEntity
import com.shashank.splitterexpensemanager.model.Group
import com.shashank.splitterexpensemanager.model.OweOrOwed
import kotlinx.coroutines.flow.Flow

interface AddExpensesRepository {
    fun loadGroup(groupId: Long): Flow<Group>
    fun loadPerson(personId: Long): Flow<Person>

    fun loadAllGroupMemberWithGroupId(groupId: Long): Flow<List<Person>>
    suspend fun insertExpenses(expenses: ExpensesEntity): Long

    suspend fun updateExpenses(expenses: ExpensesEntity)
    suspend fun insertOweOrOwed(oweOrOwed: OweOrOwedEntity)

    suspend fun updateOweOrOwed(oweOrOwed: OweOrOwedEntity)

    suspend fun loadAllOweOrOwedByExpensesId(expensesId: Long): List<OweOrOwed>

    fun loadExpensesByExpensesId(expensesId: Long): Flow<ExpenseWithCategoryAndPerson>
}