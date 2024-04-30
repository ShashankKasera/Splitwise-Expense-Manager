package com.shashank.splitterexpensemanager.feature.createfriens.repository

import com.shashank.splitterexpensemanager.authentication.personmapper.PersonMapper
import com.shashank.splitterexpensemanager.localdb.model.Person
import com.shashank.splitterexpensemanager.localdb.room.dao.PersonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateFriendsRepositoryImp @Inject constructor(
    private val personDao: PersonDao,
    private val personMapper: PersonMapper,
) : CreateFriendsRepository {
    override suspend fun insertPerson(person: Person) = withContext(Dispatchers.IO) {
        personDao.insertPerson(person)
    }

    override suspend fun updatePerson(person: Person) = withContext(Dispatchers.IO) {
        personDao.upDatePerson(person)
    }

    override fun loadPerson(personId: Long) = personDao.loadPersonByIdFlow(personId).map {
        personMapper.map(it)
    }
}