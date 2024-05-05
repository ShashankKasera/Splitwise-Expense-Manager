package com.shashank.splitterexpensemanager.mapper.oweorowedWithpersonandgroupmapper

import com.shashank.splitterexpensemanager.authentication.personmapper.PersonMapper
import com.shashank.splitterexpensemanager.core.mapper.Mapper
import com.shashank.splitterexpensemanager.mapper.groupmapper.GroupMapper
import com.shashank.splitterexpensemanager.mapper.oweorowedmapper.OweOrOwedMapper
import com.shashank.splitterexpensemanager.model.OweOrOwedWithPersonAndGroup
import javax.inject.Inject
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwedWithPersonAndGroup as OweOrOwedWithPersonAndGroupEntity

class OweOrOwedWithPersonAndGroupMapper @Inject constructor(
    val personMapper: PersonMapper,
    val oweOrOwedMapper: OweOrOwedMapper,
    val groupMapper: GroupMapper,
) : Mapper<OweOrOwedWithPersonAndGroupEntity?, OweOrOwedWithPersonAndGroup> {
    override fun map(input: OweOrOwedWithPersonAndGroupEntity?) = OweOrOwedWithPersonAndGroup(
        oweOrOwed = oweOrOwedMapper.map(input?.oweOrOwed),
        personOwe = personMapper.map(input?.personOwe),
        personOwed = personMapper.map(input?.personOwed),
        group = groupMapper.map(input?.group)
    )
}