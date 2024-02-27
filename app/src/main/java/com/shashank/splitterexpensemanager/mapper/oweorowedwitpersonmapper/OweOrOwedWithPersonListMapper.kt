package com.shashank.splitterexpensemanager.mapper.oweorowedwitpersonmapper

import com.shashank.splitterexpensemanager.core.mapper.ListMapper
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwedWithPerson as OweOrOwedWithPersonEntity
import com.shashank.splitterexpensemanager.model.OweOrOwedWithPerson
import javax.inject.Inject

class OweOrOwedWithPersonListMapper @Inject constructor(
    private val oweOrOwedWithPerson: OweOrOwedWithPersonMapper
) : ListMapper<OweOrOwedWithPersonEntity, OweOrOwedWithPerson> {
    override fun map(input: List<OweOrOwedWithPersonEntity>): List<OweOrOwedWithPerson> {
        return input.map { oweOrOwedWithPerson.map(it) }
    }
}