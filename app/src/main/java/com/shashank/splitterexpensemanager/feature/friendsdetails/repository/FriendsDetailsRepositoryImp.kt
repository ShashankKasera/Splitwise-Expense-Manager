package com.shashank.splitterexpensemanager.feature.friendsdetails.repository

import com.shashank.splitterexpensemanager.localdb.room.dao.OweOrOwedDao
import com.shashank.splitterexpensemanager.mapper.oweorowedWithpersonandgroupmapper.OweOrOwedWithPersonAndGroupListMapper
import javax.inject.Inject

class FriendsDetailsRepositoryImp @Inject constructor(
    private val oweOrOwedDao: OweOrOwedDao,
    private val oweOrOwedWithPersonAndGroupListMapper: OweOrOwedWithPersonAndGroupListMapper,
) : FriendsDetailsRepository {
    override fun loadAllOweByOweIdAndOwedId(personOweId: Long, personOwedId: Long) =
        oweOrOwedWithPersonAndGroupListMapper.map(
            oweOrOwedDao.loadAllOweByOweIdAndOwedId(personOweId, personOwedId)
        )

    override fun loadAllOwedByOweIdAndOwedId(personOweId: Long, personOwedId: Long) =
        oweOrOwedWithPersonAndGroupListMapper.map(
            oweOrOwedDao.loadAllOwedByOweIdAndOwedId(personOweId, personOwedId)
        )
}