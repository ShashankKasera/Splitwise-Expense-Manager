package com.shashank.splitterexpensemanager.feature.groupdetails.repository

import com.shashank.splitterexpensemanager.authentication.personmapper.PersonListMapper
import com.shashank.splitterexpensemanager.localdb.room.dao.ExpensesDao
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupMemberDao
import com.shashank.splitterexpensemanager.localdb.room.dao.OweOrOwedDao
import com.shashank.splitterexpensemanager.mapper.expensecategorypersonmapper.ExpenseWithCategoryAndPersonListMapper
import com.shashank.splitterexpensemanager.mapper.groupmapper.GroupMapper
import com.shashank.splitterexpensemanager.mapper.oweorowedwitpersonmapper.OweOrOwedWithPersonListMapper
import javax.inject.Inject

class GroupDetailsRepositoryImp @Inject constructor(
    private val groupDao: GroupDao,
    private val expensesDao: ExpensesDao,
    private val oweOrOwedDao: OweOrOwedDao,
    private val groupMemberDao: GroupMemberDao,
    private val groupMapper: GroupMapper,
    private val personListMapper: PersonListMapper,
    private val expenseWithCategoryAndPersonListMapper: ExpenseWithCategoryAndPersonListMapper,
    private val oweOrOwedWithPersonListMapper: OweOrOwedWithPersonListMapper,
) : GroupDetailsRepository {
    override fun loadGroup(groupId: Long) =
        groupMapper.map(groupDao.loadGroup(groupId))

    override fun loadGroupExpenses(groupId: Long) =
        expenseWithCategoryAndPersonListMapper.map(expensesDao.loadAllExpensesByGroupId(groupId))

    override fun loadAllOweByGroupId(groupId: Long, personId: Long) =
        oweOrOwedWithPersonListMapper.map(oweOrOwedDao.loadAllOweByGroupId(groupId, personId))

    override fun loadAllOwedByGroupId(groupId: Long, personId: Long) =
        oweOrOwedWithPersonListMapper.map(oweOrOwedDao.loadAllOwedByGroupId(groupId, personId))

    override fun loadAllGroupMemberWithGroupId(groupId: Long) =
        personListMapper.map(groupMemberDao.loadAllGroupMemberWithGroupId(groupId))
}