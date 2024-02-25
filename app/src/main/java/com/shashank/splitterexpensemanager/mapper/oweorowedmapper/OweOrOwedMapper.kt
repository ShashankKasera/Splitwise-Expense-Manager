package com.shashank.splitterexpensemanager.mapper.oweorowedmapper

import com.shashank.splitterexpensemanager.core.mapper.Mapper
import com.shashank.splitterexpensemanager.model.OweOrOwed
import javax.inject.Inject
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwed as OweOrOwedEntity

class OweOrOwedMapper @Inject constructor() :
    Mapper<OweOrOwedEntity, OweOrOwed> {
    override fun map(input: OweOrOwedEntity) = OweOrOwed(
        id = input.id,
        personOweId = input.personOweId,
        personOwedId = input.personOwedId,
        groupId = input.groupId,
        amount = input.amount,
    )
}