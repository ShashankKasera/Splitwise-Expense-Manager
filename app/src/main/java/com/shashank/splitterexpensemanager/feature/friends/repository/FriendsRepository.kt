package com.shashank.splitterexpensemanager.feature.friends.repository

import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.model.OweOrOwedWithPerson

interface FriendsRepository {

    fun loadPerson(): List<Person>

    fun loadAllOwe(personId: Long): List<OweOrOwedWithPerson>

    fun loadAllOwed(personId: Long): List<OweOrOwedWithPerson>
}