package com.shashank.splitterexpensemanager.feature.addgroup.data

import com.shashank.splitterexpensemanager.localdb.model.Group
import com.shashank.splitterexpensemanager.localdb.model.GroupType
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddGroupRepositoryImpl @Inject constructor(var groupDao: GroupDao): AddGroupRepository {
    override fun insertGroup(group: Group): Flow<Unit> = flow {
        groupDao.insertGroup(group)
        emit(Unit)
    }

    override fun getGroupTypeList(): Flow<List<GroupType>> {
        TODO("Not yet implemented")
    }


}