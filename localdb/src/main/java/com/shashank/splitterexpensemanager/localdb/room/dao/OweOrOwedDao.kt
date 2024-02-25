package com.shashank.splitterexpensemanager.localdb.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwed
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwedWithPerson
import kotlinx.coroutines.flow.Flow

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
    fun loadAllOweOrOwed(): Flow<List<OweOrOwed>>

    @Query("SELECT * FROM OweOrOwed WHERE OweOrOwed.groupId == :groupId AND OweOrOwed.personOweId==:personId")
    fun loadAllOweByGroupId(groupId: Long, personId: Long): List<OweOrOwedWithPerson>

    @Query("SELECT * FROM OweOrOwed WHERE OweOrOwed.groupId == :groupId AND OweOrOwed.personOweId!=:personId")
    fun loadAllOwedByGroupId(groupId: Long, personId: Long): List<OweOrOwedWithPerson>
}