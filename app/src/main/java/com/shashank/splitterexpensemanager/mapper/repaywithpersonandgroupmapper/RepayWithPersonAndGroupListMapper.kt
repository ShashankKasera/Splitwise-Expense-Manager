package com.shashank.splitterexpensemanager.mapper.repaywithpersonandgroupmapper
import com.shashank.splitterexpensemanager.core.mapper.ListMapper
import com.shashank.splitterexpensemanager.localdb.model.RepayWithPersonAndGroup as RepayWithPersonAndGroupEntity
import com.shashank.splitterexpensemanager.model.RepayWithPersonAndGroup
import javax.inject.Inject

data class RepayWithPersonAndGroupListMapper@Inject constructor(
    private val oweOrOwedWithPerson: RepayWithPersonAndGroupMapper
) : ListMapper<RepayWithPersonAndGroupEntity, RepayWithPersonAndGroup> {
    override fun map(input: List<RepayWithPersonAndGroupEntity>): List<RepayWithPersonAndGroup> {
        return input.map { oweOrOwedWithPerson.map(it) }
    }
}