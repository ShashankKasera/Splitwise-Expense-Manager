package com.shashank.splitterexpensemanager.feature.createfriens.repository

import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.localdb.model.Person as PersonEntity
import kotlinx.coroutines.flow.Flow

interface CreateFriendsRepository {
    suspend fun insertPerson(person: PersonEntity)

    suspend fun updatePerson(person: PersonEntity)
    fun loadPerson(personId: Long): Flow<Person>
}