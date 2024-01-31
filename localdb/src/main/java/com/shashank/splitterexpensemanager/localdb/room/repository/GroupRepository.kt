package com.shashank.splitterexpensemanager.localdb.room.repository

import com.shashank.splitterexpensemanager.localdb.model.Group
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GroupRepository @Inject constructor(var groupDao: GroupDao) {
    suspend fun insertGroup(group: Group) = withContext(Dispatchers.IO) {
        groupDao.insertGroup(group)
    }
    suspend fun insertAllGroup(vararg group: Group) = withContext(Dispatchers.IO) {
        groupDao.insertAllGroup(*group)
    }
    fun loadAllGroup() = groupDao.loadAllGroup()
}