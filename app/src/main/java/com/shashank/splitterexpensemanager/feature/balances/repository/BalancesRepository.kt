package com.shashank.splitterexpensemanager.feature.balances.repository

import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.model.OweOrOwedWithPerson

interface BalancesRepository {
    fun loadAllGroupMemberWithGroupId(groupId: Long): List<Person>

    fun loadAllOweOwedByGroupIdAndPersonId(groupId: Long, personId: Long): List<OweOrOwedWithPerson>
}