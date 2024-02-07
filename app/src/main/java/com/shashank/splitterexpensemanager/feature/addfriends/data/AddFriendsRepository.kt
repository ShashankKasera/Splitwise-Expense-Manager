package com.shashank.splitterexpensemanager.feature.addfriends.data

import com.shashank.splitterexpensemanager.localdb.model.GroupMember
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupMemberDao
import com.shashank.splitterexpensemanager.localdb.room.dao.PersonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddFriendsRepository @Inject constructor(
    var personDao: PersonDao,
    var groupMemberDao: GroupMemberDao
) {
    fun loadAllPerson() = personDao.loadAllPerson()
    fun loadAllGroupMember() = groupMemberDao.loadAllGroupMember()
    suspend fun insertGroupMember(groupMember: GroupMember) = withContext(Dispatchers.IO) {
        groupMemberDao.insertGroupMember(groupMember)
    }

    suspend fun deleteGroupMember(personId: Long) = withContext(Dispatchers.IO) {
        groupMemberDao.deleteGroupMember(personId)
    }
}