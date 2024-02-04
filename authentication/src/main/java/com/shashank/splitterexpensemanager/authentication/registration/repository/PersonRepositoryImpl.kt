package com.shashank.splitterexpensemanager.authentication.registration.repository

import com.shashank.splitterexpensemanager.localdb.model.Person
import com.shashank.splitterexpensemanager.localdb.room.dao.PersonDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(var personDao: PersonDao) : PersonRepository {
    override fun insertPerson(person: Person): Flow<Unit> = flow {
        personDao.insertPerson(person)
        emit(Unit)
    }
}