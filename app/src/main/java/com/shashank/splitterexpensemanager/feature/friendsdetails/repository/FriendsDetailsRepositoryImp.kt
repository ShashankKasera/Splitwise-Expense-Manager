package com.shashank.splitterexpensemanager.feature.friendsdetails.repository

import com.shashank.splitterexpensemanager.authentication.personmapper.PersonMapper
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import com.shashank.splitterexpensemanager.localdb.room.dao.OweOrOwedDao
import com.shashank.splitterexpensemanager.localdb.room.dao.PersonDao
import com.shashank.splitterexpensemanager.mapper.groupmapper.GroupListMapper
import javax.inject.Inject

class FriendsDetailsRepositoryImp @Inject constructor(
    private val oweOrOwedDao: OweOrOwedDao,
    private val groupDao: GroupDao,
    private val personDao: PersonDao,
    private val groupListMapper: GroupListMapper,
    private val personMapper: PersonMapper,
) : FriendsDetailsRepository {
    override fun loadAllGroupByFriendId(friendId: Long) = groupListMapper.map(
        groupDao.loadGroupByFriendId(friendId)
    )

    override fun loadFriendByFriendId(friendId: Long) = personMapper.map(
        personDao.loadPersonById(friendId)
    )

    override fun loadAllOweByOweIdAndOwedId(personId: Long, friendId: Long, groupId: Long) =
        oweOrOwedDao.loadAllOweByOweIdAndOwedId(personId, friendId, groupId)

    override fun loadAllOwedByOweIdAndOwedId(friendId: Long, personId: Long, groupId: Long) =
        oweOrOwedDao.loadAllOwedByOweIdAndOwedId(friendId, personId, groupId)
}