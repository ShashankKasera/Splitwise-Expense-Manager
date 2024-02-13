package com.shashank.splitterexpensemanager.feature.addexpense.repository

import com.shashank.splitterexpensemanager.localdb.model.Expenses
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwed
import com.shashank.splitterexpensemanager.model.Group
import com.shashank.splitterexpensemanager.authentication.model.Person
import kotlinx.coroutines.flow.Flow

interface AddExpensesRepository {
    fun loadGroup(groupId: Long): Flow<Group>
    fun loadPerson(personId: Long): Flow<Person>

    fun loadAllGroupMemberWithGroupId(groupId: Long): Flow<List<Person>>
    suspend fun insertExpenses(expenses: Expenses)
    suspend fun insertOweOrOwed(oweOrOwed: OweOrOwed)
}