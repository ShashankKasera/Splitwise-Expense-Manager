package com.shashank.splitterexpensemanager.feature.groupmember.repository

import com.shashank.splitterexpensemanager.authentication.model.Person
import kotlinx.coroutines.flow.Flow

interface GroupMemberRepository {
    fun loadAllGroupMemberWithGroupId(groupId: Long): Flow<List<Person>>
}