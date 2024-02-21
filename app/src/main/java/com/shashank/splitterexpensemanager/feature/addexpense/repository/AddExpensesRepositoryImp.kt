package com.shashank.splitterexpensemanager.feature.addexpense.repository

import com.shashank.splitterexpensemanager.localdb.model.Expenses
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwed
import com.shashank.splitterexpensemanager.localdb.room.dao.ExpensesDao
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupMemberDao
import com.shashank.splitterexpensemanager.localdb.room.dao.OweOrOwedDao
import com.shashank.splitterexpensemanager.localdb.room.dao.PersonDao
import com.shashank.splitterexpensemanager.mapper.groupmapper.GroupMapper
import com.shashank.splitterexpensemanager.authentication.personmapper.PersonListMapper
import com.shashank.splitterexpensemanager.authentication.personmapper.PersonMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddExpensesRepositoryImp @Inject constructor(
    private val groupDao: GroupDao,
    private val personDao: PersonDao,
    private val expensesDao: ExpensesDao,
    private val oweOrOwedDao: OweOrOwedDao,
    private val groupMemberDao: GroupMemberDao,
    private val personListMapper: PersonListMapper,
    private val personMapper: PersonMapper,
    private val groupMapper: GroupMapper
) : AddExpensesRepository {
    override fun loadGroup(groupId: Long) = groupDao.loadGroup(groupId).map {
        groupMapper.map(it)
    }

    override fun loadPerson(personId: Long) = personDao.loadPersonById(personId).map {
        personMapper.map(it)
    }

    override fun loadAllGroupMemberWithGroupId(groupId: Long) =
        groupMemberDao.loadAllGroupMemberWithGroupId(groupId).map {
            personListMapper.map(it)
        }
    override suspend fun insertExpenses(expenses: Expenses) = withContext(Dispatchers.IO) {
        expensesDao.insertExpenses(expenses)
    }

    override suspend fun insertOweOrOwed(oweOrOwed: OweOrOwed) = withContext(Dispatchers.IO) {
        oweOrOwedDao.insertOweOrOwed(oweOrOwed)
    }
}