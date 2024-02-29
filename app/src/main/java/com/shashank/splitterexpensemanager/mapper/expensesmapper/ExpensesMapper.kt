package com.shashank.splitterexpensemanager.mapper.expensesmapper

import com.shashank.splitterexpensemanager.core.extension.EMPTY
import com.shashank.splitterexpensemanager.core.mapper.Mapper
import com.shashank.splitterexpensemanager.model.Expenses
import javax.inject.Inject
import com.shashank.splitterexpensemanager.localdb.model.Expenses as ExpensesEntity

class ExpensesMapper @Inject constructor() :
    Mapper<ExpensesEntity?, Expenses> {
    override fun map(input: ExpensesEntity?) = Expenses(
        id = input?.id ?: -1,
        personId = input?.personId ?: -1,
        groupId = input?.groupId ?: -1,
        categoryId = input?.categoryId ?: -1,
        amount = input?.amount ?: 0.0,
        splitAmount = input?.splitAmount ?: 0.0,
        name = input?.name ?: String.EMPTY,
        date = input?.date ?: String.EMPTY,
        time = input?.time ?: String.EMPTY,
        description = input?.description ?: String.EMPTY,
    )
}