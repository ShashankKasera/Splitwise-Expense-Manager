package com.shashank.splitterexpensemanager.feature.addpayment.repository

import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.localdb.model.Repay
import com.shashank.splitterexpensemanager.model.OweOrOwed
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwed as OweOrOwedEntity
import com.shashank.splitterexpensemanager.model.RepayWithPerson
import kotlinx.coroutines.flow.Flow

interface AddPaymentRepository {

    suspend fun insertRepay(repay: Repay): Long

    suspend fun insertOweOrOwed(oweOrOwed: OweOrOwedEntity)

    suspend fun updateRepay(repay: Repay)

    suspend fun updateOweOrOwed(oweOrOwed: OweOrOwedEntity)

    fun loadPersonByPersonId(personId: Long): Flow<Person>

    fun loadRepayByRepayId(repayId: Long): Flow<RepayWithPerson>
    fun loadOweOrOwedByRepayId(repayId: Long): Flow<OweOrOwed>
}