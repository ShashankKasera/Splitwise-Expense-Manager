package com.shashank.splitterexpensemanager.mapper.oweorowedwithpersonmapper

import com.shashank.splitterexpensemanager.authentication.personmapper.PersonMapper
import com.shashank.splitterexpensemanager.core.mapper.Mapper
import com.shashank.splitterexpensemanager.mapper.oweorowedmapper.OweOrOwedMapper
import com.shashank.splitterexpensemanager.model.OweOrOwedWithPerson
import javax.inject.Inject
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwedWithPerson as OweOrOwedWithPersonEntity

class OweOrOwedWithPersonMapper @Inject constructor(
    val personMapper: PersonMapper,
    val oweOrOwedMapper: OweOrOwedMapper,
) : Mapper<OweOrOwedWithPersonEntity?, OweOrOwedWithPerson> {
    override fun map(input: OweOrOwedWithPersonEntity?) = OweOrOwedWithPerson(
        oweOrOwed = oweOrOwedMapper.map(input?.oweOrOwed),
        personOwed = personMapper.map(input?.personOwed),
        personOwe = personMapper.map(input?.personOwe),
    )
}