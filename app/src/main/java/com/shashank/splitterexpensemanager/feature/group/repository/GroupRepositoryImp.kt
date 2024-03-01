package com.shashank.splitterexpensemanager.feature.group.repository

import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import com.shashank.splitterexpensemanager.mapper.groupmapper.GroupListMapper
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GroupRepositoryImp @Inject constructor(
    private val groupDao: GroupDao,
    private val groupListMapper: GroupListMapper,
) : GroupRepository {
    override fun loadAllGroup() = groupDao.loadAllGroup().map {
        groupListMapper.map(it)
    }
}