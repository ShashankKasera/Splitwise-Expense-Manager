package com.shashank.splitterexpensemanager.mapper.oweorowedWithpersonandgroupmapper

import com.shashank.splitterexpensemanager.core.mapper.ListMapper
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwedWithPersonAndGroup as OweOrOwedWithPersonAndGroupEntity
import com.shashank.splitterexpensemanager.model.OweOrOwedWithPersonAndGroup
import javax.inject.Inject

class OweOrOwedWithPersonAndGroupListMapper @Inject constructor(
    private val oweOrOwedWithPerson: OweOrOwedWithPersonAndGroupMapper
) : ListMapper<OweOrOwedWithPersonAndGroupEntity, OweOrOwedWithPersonAndGroup> {
    override fun map(input: List<OweOrOwedWithPersonAndGroupEntity>): List<OweOrOwedWithPersonAndGroup> {
        return input.map { oweOrOwedWithPerson.map(it) }
    }
}