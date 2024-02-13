package com.shashank.splitterexpensemanager.feature.addfriends.repository

import com.shashank.splitterexpensemanager.localdb.model.GroupMember
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupMemberDao
import com.shashank.splitterexpensemanager.localdb.room.dao.PersonDao
import com.shashank.splitterexpensemanager.mapper.groupmembermapper.GroupMemberListMapper
import com.shashank.splitterexpensemanager.authentication.personmapper.PersonListMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddFriendsRepositoryImp @Inject constructor(
    private val personDao: PersonDao,
    private val groupMemberDao: GroupMemberDao,
    private val personListMapper: PersonListMapper,
    private val groupMemberListMapper: GroupMemberListMapper,
) : AddFriendsRepository {
    override fun loadPersonExcept(personId: Long) = personDao.getAllPersonsExcept(personId).map {
        personListMapper.map(it)
    }

    override suspend fun insertGroupMember(groupMember: GroupMember) = withContext(Dispatchers.IO) {
        groupMemberDao.insertGroupMember(groupMember)
    }

    override suspend fun deleteGroupMember(personId: Long) = withContext(Dispatchers.IO) {
        groupMemberDao.deleteGroupMember(personId)
    }

    override fun loadAllGroupMember() = groupMemberDao.loadAllGroupMember().map {
        groupMemberListMapper.map(it)
    }
}