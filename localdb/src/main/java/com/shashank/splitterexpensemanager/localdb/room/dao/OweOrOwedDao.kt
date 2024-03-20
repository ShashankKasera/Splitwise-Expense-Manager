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

    @Query("SELECT * FROM OweOrOwed WHERE OweOrOwed.groupId == :groupId AND OweOrOwed.personOwedId!=:personId")
    fun loadAllOweByGroupId(groupId: Long, personId: Long): List<OweOrOwedWithPerson>

    @Query("SELECT * FROM OweOrOwed WHERE OweOrOwed.groupId == :groupId AND OweOrOwed.personOwedId==:personId")
    fun loadAllOwedByGroupId(groupId: Long, personId: Long): List<OweOrOwedWithPerson>

    @Query("SELECT * FROM OweOrOwed WHERE OweOrOwed.expensesId == :expensesId")
    fun loadAllOweOwedWithPersonByExpensesId(expensesId: Long): Flow<List<OweOrOwedWithPerson>>

    @Query("SELECT * FROM OweOrOwed WHERE OweOrOwed.expensesId == :expensesId")
    fun loadAllOweOwedByExpensesId(expensesId: Long): List<OweOrOwed>

    @Query("SELECT * FROM OweOrOwed WHERE OweOrOwed.groupId == :groupId")
    fun loadAllOweOwedByGroupId(groupId: Long): List<OweOrOwedWithPerson>

    @Query("Select * from OweOrOwed")
    fun loadAllOweOrOwedWithPerson(): List<OweOrOwedWithPerson>

    @Query(
        "SELECT SUM(amount) " +
            "FROM OweOrOwed " +
            "WHERE personOwedId = :personId " +
            "AND personOweId = :friendId " +
            "AND groupId=:groupId"
    )
    fun loadAllOweByOweIdAndOwedId(personId: Long, friendId: Long, groupId: Long): Double

    @Query(
        "SELECT SUM(amount) " +
            "FROM OweOrOwed " +
            "WHERE OweOrOwed.personOwedId==:friendId " +
            "AND OweOrOwed.personOweId==:personId " +
            "AND groupId=:groupId"
    )
    fun loadAllOwedByOweIdAndOwedId(friendId: Long, personId: Long, groupId: Long): Double

    @Query(
        "SELECT * " +
            "FROM OweOrOwed " +
            "WHERE groupId = :groupId " +
            "AND (personOwedId = :personId " +
            "OR personOweId = :personId) " +
            "AND (personOwedId != personOweId)"
    )
    fun loadAllOweOwedByGroupIdAndPersonId(groupId: Long, personId: Long): List<OweOrOwedWithPerson>

    @Query("SELECT * FROM OweOrOwed WHERE OweOrOwed.repayId == :repayId")
    fun loadOweOwedByRepayId(repayId: Long): Flow<OweOrOwed>
}