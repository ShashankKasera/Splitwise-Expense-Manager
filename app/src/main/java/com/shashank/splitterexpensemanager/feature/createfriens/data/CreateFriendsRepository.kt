package com.shashank.splitterexpensemanager.feature.createfriens.data

import com.shashank.splitterexpensemanager.localdb.model.Person
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupMemberDao
import com.shashank.splitterexpensemanager.localdb.room.dao.PersonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateFriendsRepository @Inject constructor(var personDao: PersonDao, var groupMemberDao: GroupMemberDao) {
    suspend fun insertPerson(person: Person) = withContext(Dispatchers.IO) {
        personDao.insertPerson(person)
    }


}