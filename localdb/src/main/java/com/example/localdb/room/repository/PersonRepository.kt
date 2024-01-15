package com.example.localdb.room.repository
import com.example.localdb.model.Person
import com.example.localdb.room.dao.PersonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
class PersonRepository @Inject constructor(var personDao: PersonDao) {
    suspend fun insertAllPerson(vararg person: Person) = withContext(Dispatchers.IO) {
        personDao.insertAllPerson(*person)
    }
    fun loadAllPerson() = personDao.loadAllPerson()
}