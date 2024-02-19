package com.shashank.splitterexpensemanager.mapper.expensecategorypersonmapper

import com.shashank.splitterexpensemanager.core.mapper.ListMapper
import com.shashank.splitterexpensemanager.localdb.model.ExpenseWithCategoryAndPerson as ExpenseWithCategoryAndPersonEntity
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPerson
import javax.inject.Inject

class ExpenseWithCategoryAndPersonListMapper @Inject constructor(
    private val group: ExpenseWithCategoryAndPersonMapper
) : ListMapper<ExpenseWithCategoryAndPersonEntity, ExpenseWithCategoryAndPerson> {
    override fun map(input: List<ExpenseWithCategoryAndPersonEntity>): List<ExpenseWithCategoryAndPerson> {
        return input.map { group.map(it) }
    }
}