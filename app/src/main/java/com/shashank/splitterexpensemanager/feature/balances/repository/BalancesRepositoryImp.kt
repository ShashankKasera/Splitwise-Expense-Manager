package com.shashank.splitterexpensemanager.feature.balances.repository


import com.shashank.splitterexpensemanager.authentication.personmapper.PersonListMapper
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupMemberDao
import com.shashank.splitterexpensemanager.localdb.room.dao.OweOrOwedDao
import com.shashank.splitterexpensemanager.mapper.oweorowedwithpersonmapper.OweOrOwedWithPersonListMapper
import javax.inject.Inject

class BalancesRepositoryImp @Inject constructor(
    private val oweOrOwedDao: OweOrOwedDao,
    private val groupMemberDao: GroupMemberDao,
    private val personListMapper: PersonListMapper,
    private val oweOrOwedWithPersonListMapper: OweOrOwedWithPersonListMapper,
) : BalancesRepository {
    override fun loadAllOweOwedByGroupIdAndPersonId(groupId: Long, personId: Long) =
        oweOrOwedWithPersonListMapper.map(
            oweOrOwedDao.loadAllOweOwedByGroupIdAndPersonId(
                groupId,
                personId
            )
        )

    override fun loadAllGroupMemberWithGroupId(groupId: Long) =
        personListMapper.map(groupMemberDao.loadAllGroupMemberWithGroupId(groupId))
}