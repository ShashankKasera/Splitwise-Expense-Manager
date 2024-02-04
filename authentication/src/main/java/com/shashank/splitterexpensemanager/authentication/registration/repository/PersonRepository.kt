package com.shashank.splitterexpensemanager.authentication.registration.repository
import com.shashank.splitterexpensemanager.localdb.model.Person
import com.shashank.splitterexpensemanager.localdb.room.dao.PersonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
class PersonRepository @Inject constructor(var personDao: PersonDao) {
    suspend fun insertPerson(person: Person) = withContext(Dispatchers.IO) {
        personDao.insertAllPerson(person)
    }
    fun loadAllPerson() = personDao.loadAllPerson()
}