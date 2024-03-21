package com.shashank.splitterexpensemanager.mapper.repaywithpersonmapper

import com.shashank.splitterexpensemanager.authentication.personmapper.PersonMapper
import com.shashank.splitterexpensemanager.core.mapper.Mapper
import com.shashank.splitterexpensemanager.mapper.repaymapper.RepayMapper
import com.shashank.splitterexpensemanager.localdb.model.RepayWithPerson as RepayWithPersonEntity
import com.shashank.splitterexpensemanager.model.RepayWithPerson
import javax.inject.Inject
class RepayWithPersonMapper @Inject constructor(
    val personMapper: PersonMapper,
    val repayMapper: RepayMapper
) : Mapper<RepayWithPersonEntity?, RepayWithPerson> {
    override fun map(input: RepayWithPersonEntity?) = RepayWithPerson(
        repay = repayMapper.map(input?.repay),
        payer = personMapper.map(input?.payer),
        receiver = personMapper.map(input?.receiver)
    )
}