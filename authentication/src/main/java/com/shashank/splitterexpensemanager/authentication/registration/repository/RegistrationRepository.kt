package com.shashank.splitterexpensemanager.authentication.registration.repository

import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.localdb.model.Category
import kotlinx.coroutines.flow.Flow
import com.shashank.splitterexpensemanager.localdb.model.Person as PersonEntity

interface RegistrationRepository {
    suspend fun insertPerson(person: PersonEntity)
    fun loadPersonByEmail(email: String): Flow<Person>
    suspend fun insertAllCategory(vararg category: Category)
}