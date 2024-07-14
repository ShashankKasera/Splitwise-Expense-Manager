package com.shashank.splitterexpensemanager.mapper.expensewithcategoryandpersonandgroupmapper

import com.shashank.splitterexpensemanager.core.mapper.NullableInputListMapper
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPersonAndGroup
import javax.inject.Inject
import com.shashank.splitterexpensemanager.localdb.model.ExpenseWithCategoryAndPersonAndGroup as ExpenseWithCategoryAndPersonAndGroupEntity

class ExpenseWithCategoryAndPersonAndGroupListMapper @Inject constructor(
    private val group: ExpenseWithCategoryAndPersonAndGroupMapper
) : NullableInputListMapper<ExpenseWithCategoryAndPersonAndGroupEntity?, ExpenseWithCategoryAndPersonAndGroup> {
    override fun map(
        input: List<ExpenseWithCategoryAndPersonAndGroupEntity?>?
    ): List<ExpenseWithCategoryAndPersonAndGroup> {
        return input?.map { group.map(it) }.orEmpty()
    }
}