package com.shashank.splitterexpensemanager.mapper.repaymapper

import com.shashank.splitterexpensemanager.core.mapper.Mapper
import com.shashank.splitterexpensemanager.model.Repay
import javax.inject.Inject
import com.shashank.splitterexpensemanager.localdb.model.Repay as RepayEntity

class RepayMapper @Inject constructor() :
    Mapper<RepayEntity?, Repay> {
    override fun map(input: RepayEntity?) = Repay(
        id = input?.id,
        payerId = input?.payerId,
        receiverId = input?.receiverId,
        groupId = input?.groupId,
        amount = input?.amount,
        date = input?.date,
        time = input?.time,
        description = input?.description,
    )
}