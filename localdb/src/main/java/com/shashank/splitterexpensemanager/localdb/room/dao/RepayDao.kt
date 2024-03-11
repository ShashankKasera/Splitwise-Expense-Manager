package com.shashank.splitterexpensemanager.localdb.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.shashank.splitterexpensemanager.localdb.model.Repay

@Dao
interface RepayDao {
    @Insert
    suspend fun insertRepay(person: Repay): Long
}