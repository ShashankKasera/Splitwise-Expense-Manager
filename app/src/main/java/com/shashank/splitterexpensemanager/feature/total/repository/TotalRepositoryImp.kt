package com.shashank.splitterexpensemanager.feature.total.repository

import com.shashank.splitterexpensemanager.localdb.room.dao.ExpensesDao
import javax.inject.Inject

class TotalRepositoryImp @Inject constructor(
    private val expensesDao: ExpensesDao

) : TotalRepository {
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