package com.shashank.splitterexpensemanager.feature.groupmember.data

import com.shashank.splitterexpensemanager.localdb.room.dao.GroupMemberDao
import javax.inject.Inject

class GroupMemberRepository @Inject constructor(var groupMemberDao: GroupMemberDao) {
    fun loadAllGroupMemberWithGroupId(groupId: Long) = groupMemberDao.loadAllGroupMemberWithGroupId(groupId)
}