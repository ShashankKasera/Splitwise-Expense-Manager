package com.shashank.splitterexpensemanager.authentication.registration.repository

import com.shashank.splitterexpensemanager.localdb.model.Person
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    fun insertPerson(person: Person): Flow<Unit>
}