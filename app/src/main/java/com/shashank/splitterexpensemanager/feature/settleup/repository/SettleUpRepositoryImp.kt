package com.shashank.splitterexpensemanager.feature.settleup.repository

import com.shashank.splitterexpensemanager.localdb.room.dao.OweOrOwedDao
import com.shashank.splitterexpensemanager.mapper.oweorowedwithpersonmapper.OweOrOwedWithPersonListMapper
import javax.inject.Inject

class SettleUpRepositoryImp @Inject constructor(
    private val oweOrOwedDao: OweOrOwedDao,
    private val oweOrOwedWithPersonListMapper: OweOrOwedWithPersonListMapper,
) : SettleUpRepository {
    override fun loadAllOweByGroupId(groupId: Long, personId: Long) =
        oweOrOwedWithPersonListMapper.map(oweOrOwedDao.loadAllOweByGroupId(groupId, personId))

    override fun loadAllOwedByGroupId(groupId: Long, personId: Long) =
        oweOrOwedWithPersonListMapper.map(oweOrOwedDao.loadAllOwedByGroupId(groupId, personId))
}