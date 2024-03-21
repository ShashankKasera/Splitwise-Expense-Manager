package com.shashank.splitterexpensemanager.feature.friendsetting.repository

import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.model.Group
import kotlinx.coroutines.flow.Flow

interface FriendSettingRepository {

    fun loadFriendByFriendId(friendId: Long): Flow<Person>
    fun loadAAllGroupByFriendId(friendId: Long): Flow<List<Group>>
}