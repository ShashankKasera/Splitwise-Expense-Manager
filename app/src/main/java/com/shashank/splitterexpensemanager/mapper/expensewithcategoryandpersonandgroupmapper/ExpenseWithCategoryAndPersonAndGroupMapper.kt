package com.shashank.splitterexpensemanager.mapper.expensewithcategoryandpersonandgroupmapper

import com.shashank.splitterexpensemanager.authentication.personmapper.PersonMapper
import com.shashank.splitterexpensemanager.core.mapper.Mapper
import com.shashank.splitterexpensemanager.mapper.categorymapper.CategoryMapper
import com.shashank.splitterexpensemanager.mapper.expensesmapper.ExpensesMapper
import com.shashank.splitterexpensemanager.mapper.groupmapper.GroupMapper
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPersonAndGroup
import javax.inject.Inject
import com.shashank.splitterexpensemanager.localdb.model.ExpenseWithCategoryAndPersonAndGroup as ExpenseWithCategoryAndPersonAndGroupEntity

class ExpenseWithCategoryAndPersonAndGroupMapper @Inject constructor(
    val personMapper: PersonMapper,
    val categoryMapper: CategoryMapper,
    val expensesMapper: ExpensesMapper,
    val groupMapper: GroupMapper
) : Mapper<ExpenseWithCategoryAndPersonAndGroupEntity?, ExpenseWithCategoryAndPersonAndGroup> {
    override fun map(input: ExpenseWithCategoryAndPersonAndGroupEntity?) =
        ExpenseWithCategoryAndPersonAndGroup(
            expense = expensesMapper.map(input?.expense),
            category = categoryMapper.map(input?.category),
            person = personMapper.map(input?.person),
            group = groupMapper.map(input?.group)
        )
}