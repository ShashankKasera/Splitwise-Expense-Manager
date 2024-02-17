package com.shashank.splitterexpensemanager.feature.addgroup.repository

import com.shashank.splitterexpensemanager.localdb.model.Group
import com.shashank.splitterexpensemanager.localdb.model.GroupMember
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupMemberDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class AddGroupRepositoryImp @Inject constructor(
    private val groupDao: GroupDao,
    private val groupMemberDao: GroupMemberDao
) : AddGroupRepository {
    override suspend fun insertGroup(group: Group): Long = withContext(Dispatchers.IO) {
        groupDao.insertGroup(group)
    }

    override suspend fun insertGroupMember(groupMember: GroupMember) = withContext(Dispatchers.IO) {
        groupMemberDao.insertGroupMember(groupMember)
    }
}