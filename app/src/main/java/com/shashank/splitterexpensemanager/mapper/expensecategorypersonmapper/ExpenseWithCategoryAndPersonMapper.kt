package com.shashank.splitterexpensemanager.mapper.expensecategorypersonmapper

import com.shashank.splitterexpensemanager.authentication.personmapper.PersonMapper
import com.shashank.splitterexpensemanager.core.mapper.Mapper
import com.shashank.splitterexpensemanager.mapper.categorymapper.CategoryMapper
import com.shashank.splitterexpensemanager.mapper.expensesmapper.ExpensesMapper
import com.shashank.splitterexpensemanager.localdb.model.ExpenseWithCategoryAndPerson as ExpenseWithCategoryAndPersonEntity
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPerson
import javax.inject.Inject

class ExpenseWithCategoryAndPersonMapper @Inject constructor(
    val personMapper: PersonMapper,
    val categoryMapper: CategoryMapper,
    val expensesMapper: ExpensesMapper
) : Mapper<ExpenseWithCategoryAndPersonEntity, ExpenseWithCategoryAndPerson> {
    override fun map(input: ExpenseWithCategoryAndPersonEntity) = ExpenseWithCategoryAndPerson(
        expense = expensesMapper.map(input.expense),
        category = categoryMapper.map(input.category),
        person = personMapper.map(input.person)
    )
}