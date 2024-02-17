package com.shashank.splitterexpensemanager.authentication.registration.repository

import com.shashank.splitterexpensemanager.authentication.model.Person
import kotlinx.coroutines.flow.Flow
import com.shashank.splitterexpensemanager.localdb.model.Person as PersonEntity

interface RegistrationRepository {
    suspend fun insertPerson(person: PersonEntity)
    fun loadPersonByEmail(email: String): Flow<Person>
}