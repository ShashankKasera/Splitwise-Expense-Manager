package com.shashank.splitterexpensemanager.feature.total.repository

import com.shashank.splitterexpensemanager.localdb.room.dao.ExpensesDao
import com.shashank.splitterexpensemanager.localdb.room.dao.GroupDao
import com.shashank.splitterexpensemanager.mapper.groupmapper.GroupMapper
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TotalRepositoryImp @Inject constructor(
    private val expensesDao: ExpensesDao,
    private val groupDao: GroupDao,
    private val groupMapper: GroupMapper,
) : TotalRepository {

    override fun loadGroup(groupId: Long) = groupDao.loadGroupFlow(groupId).map {
        groupMapper.map(it)
    }
    override fun getTotalGroupSpending(groupId: Long) =
        expensesDao.getTotalGroupSpending(groupId)

    override fun getTotalGroupSpendingByMonthAndYear(
        groupId: Long,
        month: String,
        year: String
    ) = expensesDao.getTotalGroupSpendingFroThisMonth(groupId, month, year)

    override fun getTotalYouPaidFor(personId: Long, groupId: Long) =
        expensesDao.getTotalYouPaidFor(personId, groupId)

    override fun getTotalYouPaidForByMonthAndYear(
        personId: Long,
        groupId: Long,
        month: String,
        year: String
    ) =
        expensesDao.getTotalYouPaidForFroThisMonth(personId, groupId, month, year)

    override fun getYouTotalShare(groupId: Long) = expensesDao.getYourTotalShare(groupId)

    override fun getYouTotalShareByMonthAndYear(groupId: Long, month: String, year: String) =
        expensesDao.getYourTotalShareByMonthAndYear(groupId, month, year)
}