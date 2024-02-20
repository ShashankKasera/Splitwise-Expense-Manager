package com.shashank.splitterexpensemanager.feature.groupdetails.repository

import android.util.Log
import com.shashank.splitterexpensemanager.localdb.room.dao.ExpensesDao
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import com.shashank.splitterexpensemanager.localdb.room.dao.OweOrOwedDao
import com.shashank.splitterexpensemanager.mapper.expensecategorypersonmapper.ExpenseWithCategoryAndPersonListMapper
import com.shashank.splitterexpensemanager.mapper.groupmapper.GroupMapper
import com.shashank.splitterexpensemanager.mapper.oweorowedwitpersonmapper.OweOrOwedWithPersonListMapper
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GroupDetailsRepositoryImp @Inject constructor(
    private val groupDao: GroupDao,
    private val expensesDao: ExpensesDao,
    private val oweOrOwedDao: OweOrOwedDao,
    private val groupMapper: GroupMapper,
    private val expenseWithCategoryAndPersonListMapper: ExpenseWithCategoryAndPersonListMapper,
    private val oweOrOwedWithPersonListMapper: OweOrOwedWithPersonListMapper,
) : GroupDetailsRepository {
    override fun loadGroup(groupId: Long) = groupDao.loadGroup(groupId).map {
        groupMapper.map(it)
    }

    override fun loadGroupExpenses(groupId: Long) = expensesDao.loadAllExpensesByGroupId(groupId).map {
        expenseWithCategoryAndPersonListMapper.map(it)
    }

    override fun loadAllOweByGroupId(groupId: Long,personId:Long)=oweOrOwedDao.loadAllOweByGroupId(groupId,personId).map {
        oweOrOwedWithPersonListMapper.map(it)
    }
    override fun loadAllOwedByGroupId(groupId: Long,personId:Long)=oweOrOwedDao.loadAllOwedByGroupId(groupId,personId).map {
        oweOrOwedWithPersonListMapper.map(it)
    }

}