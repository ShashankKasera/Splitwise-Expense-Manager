package com.shashank.splitterexpensemanager.mapper.expensesmapper

import com.shashank.splitterexpensemanager.core.mapper.Mapper
import com.shashank.splitterexpensemanager.localdb.model.Expenses as ExpensesEntity
import com.shashank.splitterexpensemanager.model.Expenses
import javax.inject.Inject

class ExpensesMapper @Inject constructor() :
    Mapper<ExpensesEntity, Expenses> {
    override fun map(input: ExpensesEntity) = Expenses(
        id = input.id ?: 0,
        personId = input.personId,
        groupId = input.groupId,
        categoryId = input.categoryId,
        amount = input.amount,
        splitAmount = input.splitAmount,
        name = input.name,
        date = input.date,
        time = input.time,
        description = input.description,
    )
}