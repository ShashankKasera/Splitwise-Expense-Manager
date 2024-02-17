package com.shashank.splitterexpensemanager.authentication.login.repository

import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.localdb.model.Category
import com.shashank.splitterexpensemanager.localdb.model.Person as PersonEntity
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun insertPerson(person: PersonEntity)

    suspend fun insertAllCategory(vararg category: Category)

    fun loadPersonByEmail(email: String): Flow<Person>
}