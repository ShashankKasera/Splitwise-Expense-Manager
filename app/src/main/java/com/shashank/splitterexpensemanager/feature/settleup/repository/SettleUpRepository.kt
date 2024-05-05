package com.shashank.splitterexpensemanager.feature.settleup.repository

import com.shashank.splitterexpensemanager.model.OweOrOwedWithPerson

interface SettleUpRepository {

    fun loadAllOweByGroupId(groupId: Long, personId: Long): List<OweOrOwedWithPerson>

    fun loadAllOwedByGroupId(groupId: Long, personId: Long): List<OweOrOwedWithPerson>
}