package com.shashank.splitterexpensemanager.feature.friendsdetails.repository
import com.shashank.splitterexpensemanager.model.OweOrOwedWithPersonAndGroup

interface FriendsDetailsRepository {

    fun loadAllOweByOweIdAndOwedId(personOweId: Long,personOwedId: Long): List<OweOrOwedWithPersonAndGroup>

    fun loadAllOwedByOweIdAndOwedId(personOweId: Long,personOwedId: Long): List<OweOrOwedWithPersonAndGroup>
}