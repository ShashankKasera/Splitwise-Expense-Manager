package com.shashank.splitterexpensemanager.feature.account.repository

import com.shashank.splitterexpensemanager.authentication.personmapper.PersonMapper
import com.shashank.splitterexpensemanager.localdb.room.dao.PersonDao
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountRepositoryImp @Inject constructor(
    private val personDao: PersonDao,
    private val personMapper: PersonMapper
) : AccountRepository {
    override fun loadPersonById(personId: Long) = personDao.loadPersonByIdFlow(personId).map {
        personMapper.map(it)
    }
}