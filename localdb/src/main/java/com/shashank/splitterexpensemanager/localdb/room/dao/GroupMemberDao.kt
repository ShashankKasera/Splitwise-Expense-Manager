package com.shashank.splitterexpensemanager.localdb.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shashank.splitterexpensemanager.localdb.model.GroupMember
import com.shashank.splitterexpensemanager.localdb.model.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupMemberDao {
    @Insert
    suspend fun insertGroupMember(groupMember: GroupMember)

    @Update
    suspend fun upDateGroupMember(groupMember: GroupMember)

    @Insert
    fun insertAllGroupMember(vararg groupMember: GroupMember)

    @Query("Select * from GroupMember")
    fun loadAllGroupMember(): Flow<List<GroupMember>>

    @Query(
        "SELECT * " +
            "FROM GroupMember " +
            "INNER JOIN person ON Person.id == GroupMember.personId " +
            "WHERE GroupMember.groupId == :groupId"
    )
    fun loadAllGroupMemberWithGroupId(groupId: Long): Flow<List<Person>>

    @Query("DELETE FROM `GroupMember` WHERE `GroupMember`.personId = :personId;")
    fun deleteGroupMember(personId: Long)
}