package com.shashank.splitterexpensemanager.mapper.oweorowedmapper

import com.shashank.splitterexpensemanager.core.mapper.Mapper
import com.shashank.splitterexpensemanager.model.OweOrOwed
import javax.inject.Inject
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwed as OweOrOwedEntity

class OweOrOwedMapper @Inject constructor() :
    Mapper<OweOrOwedEntity?, OweOrOwed> {
    override fun map(input: OweOrOwedEntity?) = OweOrOwed(
        id = input?.id ?: -1,
        expensesId = input?.expensesId ?: -1,
        repayId = input?.expensesId ?: -1,
        personOwedId = input?.personOweId ?: -1,
        personOweId = input?.personOweId ?: -1,
        groupId = input?.groupId ?: -1,
        amount = input?.amount ?: 0.0,
    )
}