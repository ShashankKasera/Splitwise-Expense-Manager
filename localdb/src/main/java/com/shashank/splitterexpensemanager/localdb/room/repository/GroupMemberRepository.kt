package com.shashank.splitterexpensemanager.localdb.room.repository

import com.shashank.splitterexpensemanager.localdb.model.GroupMember
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupMemberDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GroupMemberRepository @Inject constructor(var groupMemberDao: GroupMemberDao) {
    suspend fun insertGroupMember(groupMember: GroupMember) = withContext(Dispatchers.IO) {
        groupMemberDao.insertGroupMember(groupMember)
    }
    suspend fun insertAllGroupMember(vararg groupMember: GroupMember) = withContext(Dispatchers.IO) {
        groupMemberDao.insertAllGroupMember(*groupMember)
    }
    fun loadAllGroupMember() = groupMemberDao.loadAllGroupMember()
}