package com.shashank.splitterexpensemanager.feature.repaydetails.repository

import com.shashank.splitterexpensemanager.model.RepayWithPerson
import kotlinx.coroutines.flow.Flow

interface RepayDetailsRepository {

    suspend fun deleteRepay(repayId: Long)
    fun loadRepayByRepayId(repayId: Long): Flow<RepayWithPerson>
}