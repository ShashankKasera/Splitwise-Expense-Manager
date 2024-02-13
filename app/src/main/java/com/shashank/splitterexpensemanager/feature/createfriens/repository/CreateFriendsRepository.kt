package com.shashank.splitterexpensemanager.feature.createfriens.repository

import com.shashank.splitterexpensemanager.localdb.model.Person

interface CreateFriendsRepository {
    suspend fun insertPerson(person: Person)
}