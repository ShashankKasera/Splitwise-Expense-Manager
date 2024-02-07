package com.shashank.splitterexpensemanager.localdb.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwed

@Dao
interface OweOrOwedDao {
    @Insert
    suspend fun insertOweOrOwed(oweOrOwed: OweOrOwed)

    @Update
    suspend fun upDateOweOrOwed(oweOrOwed: OweOrOwed)

    @Delete
    suspend fun deleteOweOrOwed(oweOrOwed: OweOrOwed)

    @Insert
    fun insertAllOweOrOwed(vararg oweOrOwed: OweOrOwed)

    @Query("Select * from OweOrOwed")
    fun loadAllOweOrOwed(): LiveData<List<OweOrOwed>>
}