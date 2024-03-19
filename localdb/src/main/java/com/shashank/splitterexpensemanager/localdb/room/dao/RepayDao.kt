package com.shashank.splitterexpensemanager.localdb.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.shashank.splitterexpensemanager.localdb.model.Repay
import com.shashank.splitterexpensemanager.localdb.model.RepayWithPerson
import com.shashank.splitterexpensemanager.localdb.model.RepayWithPersonAndGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface RepayDao {
    @Insert
    suspend fun insertRepay(person: Repay): Long

    @Query("Select * from Repay")
    fun loadAllRepay(): Flow<List<RepayWithPersonAndGroup>>

    @Transaction
    @Query("SELECT * FROM Repay WHERE Repay.groupId = :groupId")
    fun loadAllRepayByGroupId(groupId: Long): List<RepayWithPerson?>?
}