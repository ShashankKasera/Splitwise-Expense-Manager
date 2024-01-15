package com.shashank.splitterexpensemanager.localdb.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shashank.splitterexpensemanager.localdb.model.Expenses

@Dao
interface ExpensesDao {
    @Insert
    suspend fun insertExpenses(expenses: Expenses)

    @Update
    suspend fun upDateExpenses(expenses: Expenses)

    @Delete
    suspend fun deleteExpenses(expenses: Expenses)

    @Insert
    fun insertAllExpenses(vararg expenses: Expenses)

    @Query("Select * from Expenses")
    fun loadAllExpenses(): LiveData<List<Expenses>>
}