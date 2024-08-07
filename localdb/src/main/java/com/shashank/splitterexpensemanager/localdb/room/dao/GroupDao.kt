package com.shashank.splitterexpensemanager.localdb.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shashank.splitterexpensemanager.localdb.model.Group
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {
    @Insert
    suspend fun insertGroup(group: Group): Long

    @Update
    suspend fun upDateGroup(group: Group)

    @Delete
    suspend fun deleteGroup(group: Group)

    @Insert
    fun insertAllGroup(vararg group: Group)

    @Query("Select * from `Group`")
    fun loadAllGroup(): Flow<List<Group>>

    @Query("Select * from `Group` where id = :groupId")
    fun loadGroup(groupId: Long): Group

    @Query("Select * from `Group` where id = :groupId")
    fun loadGroupFlow(groupId: Long): Flow<Group>

    @Query(
        "SELECT * " +
            "FROM `GroupMember` " +
            "INNER JOIN `Group` ON `Group`.id == GroupMember.groupId " +
            "WHERE GroupMember.personId == :friendId"
    )
    fun loadGroupByFriendId(friendId: Long): List<Group>

    @Query(
        "SELECT * " +
            "FROM `GroupMember` " +
            "INNER JOIN `Group` ON `Group`.id == GroupMember.groupId " +
            "WHERE GroupMember.personId == :friendId"
    )
    fun loadGroupByFriendIdFlow(friendId: Long): Flow<List<Group>>
}