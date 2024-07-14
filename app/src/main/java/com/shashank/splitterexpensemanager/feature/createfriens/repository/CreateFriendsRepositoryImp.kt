package com.shashank.splitterexpensemanager.feature.createfriens.repository

import com.shashank.splitterexpensemanager.localdb.model.Person
import com.shashank.splitterexpensemanager.localdb.room.dao.PersonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateFriendsRepositoryImp @Inject constructor(
    private val personDao: PersonDao,
) : CreateFriendsRepository {
    override suspend fun insertPerson(person: Person) = withContext(Dispatchers.IO) {
        personDao.insertPerson(person)
    }
}