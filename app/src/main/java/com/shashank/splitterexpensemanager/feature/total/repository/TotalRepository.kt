package com.shashank.splitterexpensemanager.feature.total.repository

import com.shashank.splitterexpensemanager.model.Group
import kotlinx.coroutines.flow.Flow

interface TotalRepository {

    fun loadGroup(groupId: Long): Flow<Group>
    fun getTotalGroupSpending(groupId: Long): Flow<Double>

    fun getTotalGroupSpendingByMonthAndYear(
        groupId: Long,
        month: String,
        year: String
    ): Flow<Double>

    fun getTotalYouPaidFor(personId: Long, groupId: Long): Flow<Double>

    fun getTotalYouPaidForByMonthAndYear(
        personId: Long,
        groupId: Long,
        month: String,
        year: String
    ): Flow<Double>

    fun getYouTotalShare(groupId: Long): Flow<Double>

    fun getYouTotalShareByMonthAndYear(groupId: Long, month: String, year: String): Flow<Double>
}