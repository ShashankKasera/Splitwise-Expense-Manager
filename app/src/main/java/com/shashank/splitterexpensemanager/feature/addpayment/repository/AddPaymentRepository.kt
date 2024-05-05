package com.shashank.splitterexpensemanager.feature.addpayment.repository

import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwed
import com.shashank.splitterexpensemanager.localdb.model.Repay
import kotlinx.coroutines.flow.Flow

interface AddPaymentRepository {

    suspend fun insertRepay(repay: Repay): Long

    suspend fun insertOweOrOwed(oweOrOwed: OweOrOwed)

    fun loadPersonByPersonId(personId: Long): Flow<Person>
}