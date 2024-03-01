package com.shashank.splitterexpensemanager.feature.groupmember.repository

import com.shashank.splitterexpensemanager.authentication.personmapper.PersonListMapper
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupMemberDao
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GroupMemberRepositoryImp @Inject constructor(
    private val groupMemberDao: GroupMemberDao,
    private val personListMapper: PersonListMapper
) : GroupMemberRepository {
    override fun loadAllGroupMemberWithGroupId(groupId: Long) =
        groupMemberDao.loadAllGroupMemberWithGroupIdFlow(groupId).map {
            personListMapper.map(it)
        }
}