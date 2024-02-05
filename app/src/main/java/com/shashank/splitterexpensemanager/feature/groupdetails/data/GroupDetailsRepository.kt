package com.shashank.splitterexpensemanager.feature.groupdetails.data

import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import javax.inject.Inject
class GroupDetailsRepository @Inject constructor(var groupDao: GroupDao) {
    fun loadGroup(groupId: Long) = groupDao.loadGroup(groupId)
}