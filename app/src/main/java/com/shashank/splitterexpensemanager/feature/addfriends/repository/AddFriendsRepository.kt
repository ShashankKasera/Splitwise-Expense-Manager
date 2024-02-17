package com.shashank.splitterexpensemanager.feature.addfriends.repository

import com.shashank.splitterexpensemanager.localdb.model.GroupMember as GroupMemberEntity
import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.model.GroupMember
import kotlinx.coroutines.flow.Flow

interface AddFriendsRepository {
    fun loadPersonExcept(personId: Long): Flow<List<Person>>

    suspend fun insertGroupMember(groupMember: GroupMemberEntity)

    suspend fun deleteGroupMember(personId: Long)

    fun loadAllGroupMember(): Flow<List<GroupMember>>
}