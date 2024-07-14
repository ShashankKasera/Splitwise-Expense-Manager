package com.shashank.splitterexpensemanager.feature.addgroup.repository

import com.shashank.splitterexpensemanager.localdb.model.Group
import com.shashank.splitterexpensemanager.localdb.model.GroupMember
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupMemberDao
import com.shashank.splitterexpensemanager.mapper.groupmapper.GroupMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddGroupRepositoryImp @Inject constructor(
    private val groupDao: GroupDao,
    private val groupMapper: GroupMapper,
    private val groupMemberDao: GroupMemberDao
) : AddGroupRepository {
    override suspend fun insertGroup(group: Group): Long = withContext(Dispatchers.IO) {
        groupDao.insertGroup(group)
    }

    override suspend fun insertGroupMember(groupMember: GroupMember) = withContext(Dispatchers.IO) {
        groupMemberDao.insertGroupMember(groupMember)
    }

    override suspend fun updateGroup(group: Group) = withContext(Dispatchers.IO) {
        groupDao.upDateGroup(group)
    }

    override fun loadGroup(groupId: Long) = groupDao.loadGroupFlow(groupId).map {
        groupMapper.map(it)
    }
}