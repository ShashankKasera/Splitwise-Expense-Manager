package com.shashank.splitterexpensemanager.feature.addgroup.repository

import com.shashank.splitterexpensemanager.localdb.model.GroupMember
import com.shashank.splitterexpensemanager.model.Group
import kotlinx.coroutines.flow.Flow
import com.shashank.splitterexpensemanager.localdb.model.Group as GroupEntity


interface AddGroupRepository {
    suspend fun insertGroup(group: GroupEntity): Long
    suspend fun insertGroupMember(groupMember: GroupMember)
    suspend fun updateGroup(group: GroupEntity)

    fun loadGroup(groupId: Long): Flow<Group>
}