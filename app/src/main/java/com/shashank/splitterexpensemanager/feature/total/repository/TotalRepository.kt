package com.shashank.splitterexpensemanager.feature.total.repository

import kotlinx.coroutines.flow.Flow

interface TotalRepository {

    fun getTotalGroupSpending(groupId: Long): Flow<Double>

    fun getTotalYouPaidFor(personId: Long, groupId: Long): Flow<Double>
    fun getYouTotalShare(groupId: Long): Flow<Double>
}