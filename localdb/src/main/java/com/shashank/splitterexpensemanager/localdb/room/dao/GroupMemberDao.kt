package com.shashank.splitterexpensemanager.localdb.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shashank.splitterexpensemanager.localdb.model.GroupMember

@Dao
interface GroupMemberDao {
    @Insert
    suspend fun insertGroupMember(groupMember: GroupMember)

    @Update
    suspend fun upDateGroupMember(groupMember: GroupMember)

    @Insert
    fun insertAllGroupMember(vararg groupMember: GroupMember)

    @Query("Select * from GroupMember")
    fun loadAllGroupMember(): LiveData<List<GroupMember>>

    @Query("DELETE FROM `GroupMember` WHERE `GroupMember`.personId = :personId;")
    fun deleteGroupMember(personId: Long)
}