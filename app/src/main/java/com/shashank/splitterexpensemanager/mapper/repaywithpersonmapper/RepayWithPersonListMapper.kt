package com.shashank.splitterexpensemanager.mapper.repaywithpersonmapper
import com.shashank.splitterexpensemanager.core.mapper.NullableInputListMapper
import com.shashank.splitterexpensemanager.model.RepayWithPerson
import javax.inject.Inject
import com.shashank.splitterexpensemanager.localdb.model.RepayWithPerson as RepayWithPersonEntity

class RepayWithPersonListMapper @Inject constructor(
    private val group: RepayWithPersonMapper
) : NullableInputListMapper<RepayWithPersonEntity?, RepayWithPerson> {
    override fun map(input: List<RepayWithPersonEntity?>?): List<RepayWithPerson> {
        return input?.map { group.map(it) }.orEmpty()
    }
}