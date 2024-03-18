package com.shashank.splitterexpensemanager.mapper.oweorowedmapper

import com.shashank.splitterexpensemanager.core.mapper.ListMapper
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwed as OweOrOwedEntity
import com.shashank.splitterexpensemanager.model.OweOrOwed
import javax.inject.Inject

class OweOrOwedListMapper @Inject constructor(
    private val oweOrOwed: OweOrOwedMapper
) : ListMapper<OweOrOwedEntity, OweOrOwed> {
    override fun map(input: List<OweOrOwedEntity>): List<OweOrOwed> {
        return input.map { oweOrOwed.map(it) }
    }
}