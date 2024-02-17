package com.shashank.splitterexpensemanager.authentication.registration.repository

import com.shashank.splitterexpensemanager.authentication.personmapper.PersonMapper
import com.shashank.splitterexpensemanager.localdb.model.Category
import com.shashank.splitterexpensemanager.localdb.model.Person
import com.shashank.splitterexpensemanager.localdb.room.dao.CategoryDao
import com.shashank.splitterexpensemanager.localdb.room.dao.PersonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RegistrationRepositoryImp @Inject constructor(
    private val personDao: PersonDao,
    private val categoryDao: CategoryDao,
    private val personMapper: PersonMapper,
) : RegistrationRepository {
    override suspend fun insertPerson(person: Person) = withContext(Dispatchers.IO) {
        personDao.insertPerson(person)
    }

    override suspend fun insertAllCategory(vararg category: Category) =
        withContext(Dispatchers.IO) {
            categoryDao.insertAllCategory(*category)
        }

    override fun loadPersonByEmail(email: String) = personDao.loadPersonByEmail(email).map {
        personMapper.map(it)
    }
}