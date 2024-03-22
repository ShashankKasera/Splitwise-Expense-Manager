package com.shashank.splitterexpensemanager.mapper.repaywithpersonandgroupmapper

import com.shashank.splitterexpensemanager.authentication.personmapper.PersonMapper
import com.shashank.splitterexpensemanager.core.mapper.Mapper
import com.shashank.splitterexpensemanager.mapper.groupmapper.GroupMapper
import com.shashank.splitterexpensemanager.mapper.repaymapper.RepayMapper
import com.shashank.splitterexpensemanager.model.RepayWithPersonAndGroup
import javax.inject.Inject
import com.shashank.splitterexpensemanager.localdb.model.RepayWithPersonAndGroup as RepayWithPersonAndGroupEntity

data class RepayWithPersonAndGroupMapper @Inject constructor(
    val personMapper: PersonMapper,
    val repayMapper: RepayMapper,
    val groupMapper: GroupMapper
) : Mapper<RepayWithPersonAndGroupEntity?, RepayWithPersonAndGroup> {
    override fun map(input: RepayWithPersonAndGroupEntity?) = RepayWithPersonAndGroup(
        repay = repayMapper.map(input?.repay),
        payer = personMapper.map(input?.payer),
        receiver = personMapper.map(input?.receiver),
        group = groupMapper.map(input?.group)
    )
}