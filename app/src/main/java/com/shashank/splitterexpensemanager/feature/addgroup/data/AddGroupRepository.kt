package com.shashank.splitterexpensemanager.feature.addgroup.data

import com.shashank.splitterexpensemanager.localdb.model.Group
import com.shashank.splitterexpensemanager.localdb.model.GroupType
import kotlinx.coroutines.flow.Flow

interface AddGroupRepository {
    fun insertGroup(group: Group) : Flow<Unit>
    fun getGroupTypeList() : Flow<List<GroupType>>

}