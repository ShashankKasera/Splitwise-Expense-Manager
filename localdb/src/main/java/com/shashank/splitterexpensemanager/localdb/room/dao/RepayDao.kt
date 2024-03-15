package com.shashank.splitterexpensemanager.localdb.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.shashank.splitterexpensemanager.localdb.model.Repay
import com.shashank.splitterexpensemanager.localdb.model.RepayWithPerson

@Dao
interface RepayDao {
    @Insert
    suspend fun insertRepay(person: Repay): Long

    @Transaction
    @Query("SELECT * FROM Repay WHERE Repay.groupId = :groupId")
    fun loadAllRepayByGroupId(groupId: Long): List<RepayWithPerson?>?
}