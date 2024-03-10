package com.shashank.splitterexpensemanager.feature.addpayment.repository

import com.shashank.splitterexpensemanager.authentication.personmapper.PersonMapper
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwed
import com.shashank.splitterexpensemanager.localdb.model.Repay
import com.shashank.splitterexpensemanager.localdb.room.dao.OweOrOwedDao
import com.shashank.splitterexpensemanager.localdb.room.dao.PersonDao
import com.shashank.splitterexpensemanager.localdb.room.dao.RepayDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddPaymentRepositoryImp @Inject constructor(
    private val repayDao: RepayDao,
    private val personDao: PersonDao,
    private val oweOrOwedDao: OweOrOwedDao,
    private val personMapper: PersonMapper,
) : AddPaymentRepository {
    override suspend fun insertRepay(repay: Repay) = withContext(Dispatchers.IO) {
        repayDao.insertRepay(repay)
    }

    override suspend fun insertOweOrOwed(oweOrOwed: OweOrOwed) = withContext(Dispatchers.IO) {
        oweOrOwedDao.insertOweOrOwed(oweOrOwed)
    }

    override fun loadPersonByPersonId(personId: Long) = personDao.loadPersonByIdFlow(personId).map {
        personMapper.map(it)
    }
}