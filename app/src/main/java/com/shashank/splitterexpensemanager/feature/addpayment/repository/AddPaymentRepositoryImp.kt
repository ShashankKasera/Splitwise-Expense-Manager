package com.shashank.splitterexpensemanager.feature.addpayment.repository

import com.shashank.splitterexpensemanager.authentication.personmapper.PersonMapper
import com.shashank.splitterexpensemanager.localdb.model.OweOrOwed
import com.shashank.splitterexpensemanager.localdb.model.Repay
import com.shashank.splitterexpensemanager.localdb.room.dao.OweOrOwedDao
import com.shashank.splitterexpensemanager.localdb.room.dao.PersonDao
import com.shashank.splitterexpensemanager.localdb.room.dao.RepayDao
import com.shashank.splitterexpensemanager.mapper.oweorowedmapper.OweOrOwedMapper
import com.shashank.splitterexpensemanager.mapper.repaywithpersonmapper.RepayWithPersonMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddPaymentRepositoryImp @Inject constructor(
    private val repayDao: RepayDao,
    private val personDao: PersonDao,
    private val oweOrOwedDao: OweOrOwedDao,
    private val personMapper: PersonMapper,
    private val repayWithPersonMapper: RepayWithPersonMapper,
    private val oweOrOwedMapper: OweOrOwedMapper,
) : AddPaymentRepository {
    override suspend fun insertRepay(repay: Repay) = withContext(Dispatchers.IO) {
        repayDao.insertRepay(repay)
    }

    override suspend fun insertOweOrOwed(oweOrOwed: OweOrOwed) = withContext(Dispatchers.IO) {
        oweOrOwedDao.insertOweOrOwed(oweOrOwed)
    }

    override suspend fun updateRepay(repay: Repay) = withContext(Dispatchers.IO) {
        repayDao.upDateRepay(repay)
    }

    override suspend fun updateOweOrOwed(oweOrOwed: OweOrOwed) = withContext(Dispatchers.IO) {
        oweOrOwedDao.upDateOweOrOwed(oweOrOwed)
    }

    override fun loadPersonByPersonId(personId: Long) = personDao.loadPersonByIdFlow(personId).map {
        personMapper.map(it)
    }

    override fun loadRepayByRepayId(repayId: Long) = repayDao.loadRepayByRepayId(repayId).map {
        repayWithPersonMapper.map(it)
    }

    override fun loadOweOrOwedByRepayId(repayId: Long) =
        oweOrOwedDao.loadOweOwedByRepayId(repayId).map {
            oweOrOwedMapper.map(it)
        }
}