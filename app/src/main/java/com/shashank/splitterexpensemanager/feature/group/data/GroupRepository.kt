package com.shashank.splitterexpensemanager.feature.group.data

import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import javax.inject.Inject

class GroupRepository @Inject constructor(var groupDao: GroupDao) {
    fun loadAllGroup() = groupDao.loadAllGroup()
}