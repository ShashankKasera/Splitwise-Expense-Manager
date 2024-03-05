package com.shashank.splitterexpensemanager.feature.group.repository

import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import com.shashank.splitterexpensemanager.localdb.room.dao.OweOrOwedDao
import com.shashank.splitterexpensemanager.mapper.groupmapper.GroupListMapper
import com.shashank.splitterexpensemanager.mapper.oweorowedwitpersonmapper.OweOrOwedWithPersonListMapper
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GroupRepositoryImp @Inject constructor(
    private val groupDao: GroupDao,
    private val oweOrOwedDao: OweOrOwedDao,
    private val groupListMapper: GroupListMapper,
    private val oweOrOwedWithPersonListMapper: OweOrOwedWithPersonListMapper,
) : GroupRepository {
    override fun loadAllGroup() = groupDao.loadAllGroup().map {
        groupListMapper.map(it)
    }

    override fun loadAllOweByGroupId(groupId: Long, personId: Long) =
        oweOrOwedWithPersonListMapper.map(oweOrOwedDao.loadAllOweByGroupId(groupId, personId))

    override fun loadAllOwedByGroupId(groupId: Long, personId: Long) =
        oweOrOwedWithPersonListMapper.map(oweOrOwedDao.loadAllOwedByGroupId(groupId, personId))
}