package com.shashank.splitterexpensemanager.feature.friends.repository

import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.model.Group

interface FriendsRepository {

    fun getAllPersonsExcept(personId: Long): List<Person>


    fun loadAllGroupByFriendId(friendId: Long): List<Group>
    fun loadAllOweByOweIdAndOwedId(personId: Long, friendId: Long, groupId: Long): Double

    fun loadAllOwedByOweIdAndOwedId(friendId: Long, personId: Long, groupId: Long): Double
}