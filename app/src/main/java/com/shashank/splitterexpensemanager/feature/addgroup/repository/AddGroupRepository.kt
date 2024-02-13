package com.shashank.splitterexpensemanager.feature.addgroup.repository

import com.shashank.splitterexpensemanager.localdb.model.Group
import com.shashank.splitterexpensemanager.localdb.model.GroupMember

interface AddGroupRepository {
    suspend fun insertGroup(group: Group): Long

    suspend fun insertGroupMember(groupMember: GroupMember)
}