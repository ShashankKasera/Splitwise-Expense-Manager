package com.shashank.splitterexpensemanager.feature.friends.repository

import com.shashank.splitterexpensemanager.authentication.personmapper.PersonListMapper
import com.shashank.splitterexpensemanager.localdb.room.dao.OweOrOwedDao
import com.shashank.splitterexpensemanager.localdb.room.dao.PersonDao
import com.shashank.splitterexpensemanager.mapper.oweorowedwitpersonmapper.OweOrOwedWithPersonListMapper
import javax.inject.Inject

class FriendsRepositoryImp @Inject constructor(
    private val personDao: PersonDao,
    private val oweOrOwedDao: OweOrOwedDao,
    private val personListMapper: PersonListMapper,
    private val oweOrOwedWithPersonListMapper: OweOrOwedWithPersonListMapper,
) : FriendsRepository {
    override fun loadPerson() = personListMapper.map(personDao.loadAllPerson())
    override fun loadAllOwe(personId: Long) =
        oweOrOwedWithPersonListMapper.map(oweOrOwedDao.loadAllOwe(personId))

    override fun loadAllOwed(personId: Long) =
        oweOrOwedWithPersonListMapper.map(oweOrOwedDao.loadAllOwed(personId))
}