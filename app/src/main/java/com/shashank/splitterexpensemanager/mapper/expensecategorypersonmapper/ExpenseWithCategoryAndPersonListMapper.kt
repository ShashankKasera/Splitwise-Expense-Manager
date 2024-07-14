package com.shashank.splitterexpensemanager.mapper.expensecategorypersonmapper

import com.shashank.splitterexpensemanager.core.mapper.NullableInputListMapper
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPerson
import javax.inject.Inject
import com.shashank.splitterexpensemanager.localdb.model.ExpenseWithCategoryAndPerson as ExpenseWithCategoryAndPersonEntity

class ExpenseWithCategoryAndPersonListMapper @Inject constructor(
    private val group: ExpenseWithCategoryAndPersonMapper
) : NullableInputListMapper<ExpenseWithCategoryAndPersonEntity?, ExpenseWithCategoryAndPerson> {
    override fun map(input: List<ExpenseWithCategoryAndPersonEntity?>?): List<ExpenseWithCategoryAndPerson> {
        return input?.map { group.map(it) }.orEmpty()
    }
}