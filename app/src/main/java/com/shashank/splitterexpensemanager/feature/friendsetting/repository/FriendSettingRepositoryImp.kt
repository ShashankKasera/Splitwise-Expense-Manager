package com.shashank.splitterexpensemanager.feature.friendsetting.repository

import com.shashank.splitterexpensemanager.authentication.personmapper.PersonMapper
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import com.shashank.splitterexpensemanager.localdb.room.dao.PersonDao
import com.shashank.splitterexpensemanager.mapper.groupmapper.GroupListMapper
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FriendSettingRepositoryImp @Inject constructor(
    private val groupDao: GroupDao,
    private val personDao: PersonDao,
    private val groupListMapper: GroupListMapper,
    private val personMapper: PersonMapper,
) : FriendSettingRepository {

    override fun loadFriendByFriendId(friendId: Long) = personDao.loadPersonByIdFlow(friendId).map {
        personMapper.map(it)
    }

    override fun loadAAllGroupByFriendId(friendId: Long) =
        groupDao.loadGroupByFriendIdFlow(friendId).map {
            groupListMapper.map(it)
        }
}