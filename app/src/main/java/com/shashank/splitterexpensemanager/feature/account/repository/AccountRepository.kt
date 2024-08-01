package com.shashank.splitterexpensemanager.feature.account.repository


import com.shashank.splitterexpensemanager.authentication.model.Person
import kotlinx.coroutines.flow.Flow
interface AccountRepository {

    fun loadPersonById(personId: Long): Flow<Person>
}