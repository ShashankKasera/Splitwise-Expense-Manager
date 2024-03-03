package com.shashank.splitterexpensemanager.feature.groupsettings.repository

import com.shashank.splitterexpensemanager.authentication.personmapper.PersonListMapper
import com.shashank.splitterexpensemanager.localdb.model.Group
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupMemberDao
import com.shashank.splitterexpensemanager.localdb.room.dao.OweOrOwedDao
import com.shashank.splitterexpensemanager.mapper.groupmapper.GroupMapper
import com.shashank.splitterexpensemanager.mapper.oweorowedwitpersonmapper.OweOrOwedWithPersonListMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import javax.inject.Inject

class GroupSettingsRepositoryImp @Inject constructor(
    private val groupDao: GroupDao,
    private val oweOrOwedDao: OweOrOwedDao,
    private val groupMemberDao: GroupMemberDao,
    private val groupMapper: GroupMapper,
    private val personListMapper: PersonListMapper,
    private val oweOrOwedWithPersonListMapper: OweOrOwedWithPersonListMapper,
) : GroupSettingsRepository {
    override fun loadGroup(groupId: Long) =
        groupMapper.map(groupDao.loadGroup(groupId))

    override fun loadAllGroupMemberWithGroupId(groupId: Long) =
        personListMapper.map(groupMemberDao.loadAllGroupMemberWithGroupId(groupId))

    override suspend fun deleteGroup(group: Group) = withContext(Dispatchers.IO) {
        groupDao.deleteGroup(group)
    }

    override fun loadAllOweOwedByGroupId(groupId: Long) =
        oweOrOwedWithPersonListMapper.map(oweOrOwedDao.loadAllOweOwedByGroupId(groupId))
}