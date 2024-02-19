package com.shashank.splitterexpensemanager.feature.groupdetails.repository

import com.shashank.splitterexpensemanager.localdb.room.dao.ExpensesDao
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import com.shashank.splitterexpensemanager.mapper.expensecategorypersonmapper.ExpenseWithCategoryAndPersonListMapper
import com.shashank.splitterexpensemanager.mapper.groupmapper.GroupMapper
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GroupDetailsRepositoryImp @Inject constructor(
    private val groupDao: GroupDao,
    private val expensesDao: ExpensesDao,
    private val groupMapper: GroupMapper,
    private val expenseWithCategoryAndPersonListMapper: ExpenseWithCategoryAndPersonListMapper,
) : GroupDetailsRepository {
    override fun loadGroup(groupId: Long) = groupDao.loadGroup(groupId).map {
        groupMapper.map(it)
    }

    override fun loadGroupExpenses(groupId: Long) = expensesDao.loadAllExpensesByGroupId(groupId).map {
        expenseWithCategoryAndPersonListMapper.map(it)
    }
}