package com.shashank.splitterexpensemanager.localdb.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.shashank.splitterexpensemanager.localdb.model.ExpenseWithCategoryAndPerson
import com.shashank.splitterexpensemanager.localdb.model.ExpenseWithCategoryAndPersonAndGroup
import com.shashank.splitterexpensemanager.localdb.model.Expenses
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpensesDao {
    @Insert
    suspend fun insertExpenses(expenses: Expenses): Long

    @Update
    suspend fun upDateExpenses(expenses: Expenses)

    @Delete
    suspend fun deleteExpenses(expenses: Expenses)

    @Insert
    fun insertAllExpenses(vararg expenses: Expenses)

    @Query("Select * from Expenses")
    fun loadAllExpenses(): Flow<List<ExpenseWithCategoryAndPersonAndGroup>>

    @Transaction
    @Query("SELECT * FROM Expenses WHERE Expenses.groupId == :groupId")
    fun loadAllExpensesByGroupIdFlow(groupId: Long): Flow<List<ExpenseWithCategoryAndPerson>>

    @Transaction
    @Query("SELECT * FROM Expenses WHERE Expenses.groupId = :groupId")
    fun loadAllExpensesByGroupId(groupId: Long): List<ExpenseWithCategoryAndPerson?>?

    @Transaction
    @Query("SELECT * FROM Expenses WHERE Expenses.id == :expensesId")
    fun loadExpensesByExpensesId(expensesId: Long): Flow<ExpenseWithCategoryAndPerson?>

    @Query("DELETE FROM `Expenses` WHERE `Expenses`.id = :expensesId;")
    fun deleteExpenses(expensesId: Long)
}