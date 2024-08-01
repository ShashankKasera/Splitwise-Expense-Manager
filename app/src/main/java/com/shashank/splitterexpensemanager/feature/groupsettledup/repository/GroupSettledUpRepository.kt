package com.shashank.splitterexpensemanager.feature.groupsettledup.repository

import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.model.Group

interface GroupSettledUpRepository {


    fun loadAllGroupByFriendId(friendId: Long): List<Group>
    fun loadFriendByFriendId(friendId: Long): Person
    fun loadAllOweByOweIdAndOwedId(personId: Long, friendId: Long, groupId: Long): Double

    fun loadAllOwedByOweIdAndOwedId(friendId: Long, personId: Long, groupId: Long): Double
}