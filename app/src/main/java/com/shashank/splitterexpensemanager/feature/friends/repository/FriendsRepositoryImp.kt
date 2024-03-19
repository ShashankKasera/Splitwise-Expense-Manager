package com.shashank.splitterexpensemanager.feature.friends.repository

import com.shashank.splitterexpensemanager.authentication.personmapper.PersonListMapper
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import com.shashank.splitterexpensemanager.localdb.room.dao.OweOrOwedDao
import com.shashank.splitterexpensemanager.localdb.room.dao.PersonDao
import com.shashank.splitterexpensemanager.mapper.groupmapper.GroupListMapper
import javax.inject.Inject

class FriendsRepositoryImp @Inject constructor(
    private val personDao: PersonDao,
    private val oweOrOwedDao: OweOrOwedDao,
    private val groupDao: GroupDao,
    private val personListMapper: PersonListMapper,
    private val groupListMapper: GroupListMapper,
) : FriendsRepository {
    override fun getAllPersonsExcept(personId: Long) = personListMapper.map(personDao.getAllPersonsExcept(personId))

    override fun loadAllGroupByFriendId(friendId: Long) = groupListMapper.map(
        groupDao.loadGroupByFriendId(friendId)
    )

    override fun loadAllOweByOweIdAndOwedId(personId: Long, friendId: Long, groupId: Long) =
        oweOrOwedDao.loadAllOweByOweIdAndOwedId(personId, friendId, groupId)

    override fun loadAllOwedByOweIdAndOwedId(friendId: Long, personId: Long, groupId: Long) =
        oweOrOwedDao.loadAllOwedByOweIdAndOwedId(friendId, personId, groupId)
}