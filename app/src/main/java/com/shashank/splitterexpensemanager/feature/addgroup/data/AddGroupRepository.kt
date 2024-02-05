package com.shashank.splitterexpensemanager.feature.addgroup.data

import com.shashank.splitterexpensemanager.localdb.model.Group
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddGroupRepository @Inject constructor(var groupDao: GroupDao) {
    suspend fun insertGroup(group: Group) = withContext(Dispatchers.IO) {
        groupDao.insertGroup(group)
    }
}